#include "ImageDAOMySQL.hpp"

#include "../MySQLManager.hpp"
#include "../../Entity/DAO/DAOException.hpp"

#include <cppconn/prepared_statement.h>
#include <cppconn/resultset.h>

static const char *ADD_IMAGE = "INSERT INTO Image (description, food_id, user_id) VALUES (?, ?, ?)";
static const char *DELETE_IMAGE = "DELETE FROM Image WHERE id = ?";
static const char *UPDATE_IMAGE = "UPDATE Image SET description = ?, food_id = ?, user_id = ? WHERE id = ?";
static const char *IMAGE_BY_ID = "SELECT id, description, food_id, user_id FROM Image WHERE id = ?";
static const char *IMAGE_BY_USERID = "SELECT id, description, food_id, user_id FROM Image WHERE user_id = ?";
static const char *IMAGE_BY_FOODID = "SELECT id, description, food_id, user_id FROM Image WHERE food_id = ?";

void ImageDAOMySQL::addImage(Image &image, int &newId)
{
    MySQLManager::ConnectionHolder ch(MySQLManager::getInstance());
    std::unique_ptr<sql::PreparedStatement> ps(ch.conn->prepareStatement(ADD_IMAGE));

    ps->setString(1, image.getDescription());
    ps->setInt(2, image.getFoodId());
    ps->setInt(3, image.getUserId());

    ps->execute();
}

void ImageDAOMySQL::deleteImage(Image &image)
{
    MySQLManager::ConnectionHolder ch(MySQLManager::getInstance());
    std::unique_ptr<sql::PreparedStatement> ps(ch.conn->prepareStatement(DELETE_IMAGE));

    ps->setInt(1, image.getId());

    ps->execute();
}

void ImageDAOMySQL::updateImage(const Image &image)
{
    MySQLManager::ConnectionHolder ch(MySQLManager::getInstance());
    std::unique_ptr<sql::PreparedStatement> ps(ch.conn->prepareStatement(UPDATE_IMAGE));

    ps->setString(1, image.getDescription());
    ps->setInt(2, image.getFoodId());
    ps->setInt(3, image.getUserId());
    ps->setInt(4, image.getId());

    ps->executeUpdate();
}

Image ImageDAOMySQL::getImageById(const int &id)
{
    MySQLManager::ConnectionHolder ch(MySQLManager::getInstance());
    std::unique_ptr<sql::PreparedStatement> ps(ch.conn->prepareStatement(IMAGE_BY_ID));

    ps->setInt(1, id);

    std::unique_ptr<sql::ResultSet> res(ps->executeQuery());

    if(!res->next())
    {
        throw DAOException("Image not found!");
    }

    return Image(res->getInt(1), res->getString(2), res->getInt(3), res->getInt(4));
}

std::vector<Image> ImageDAOMySQL::getImageByUserId(const int &userId)
{
    MySQLManager::ConnectionHolder ch(MySQLManager::getInstance());
    std::unique_ptr<sql::PreparedStatement> ps(ch.conn->prepareStatement(IMAGE_BY_USERID));

    ps->setInt(1, userId);

    std::unique_ptr<sql::ResultSet> res(ps->executeQuery());

    std::vector<Image> images;
    while(res->next())
    {
        images.push_back(Image(res->getInt(1), res->getString(2), res->getInt(3), res->getInt(4)));
    }

    return images;
}

std::vector<Image> ImageDAOMySQL::getImageByFoodId(const int &foodId)
{
    MySQLManager::ConnectionHolder ch(MySQLManager::getInstance());
    std::unique_ptr<sql::PreparedStatement> ps(ch.conn->prepareStatement(IMAGE_BY_FOODID));

    ps->setInt(1, foodId);

    std::unique_ptr<sql::ResultSet> res(ps->executeQuery());

    std::vector<Image> images;
    while(res->next())
    {
        images.push_back(Image(res->getInt(1), res->getString(2), res->getInt(3), res->getInt(4)));
    }

    return images;
}
