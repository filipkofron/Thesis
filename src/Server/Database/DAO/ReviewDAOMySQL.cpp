#include "ReviewDAOMySQL.hpp"

#include "../MySQLManager.hpp"
#include "../../Entity/DAO/DAOException.hpp"

#include <cppconn/prepared_statement.h>
#include <cppconn/resultset.h>

static const char *ADD_REVIEW = "INSERT INTO Review (user_id, added_on, review_text, food_id, rating) VALUES (?, ?, ?, ?, ?)";
static const char *DELETE_REVIEW = "DELETE FROM Review WHERE id = ?";
static const char *UPDATE_REVIEW = "UPDATE Review SET user_id = ?, added_on = ?, review_text = ?, food_id = ?, rating = ? WHERE id = ?";
static const char *REVIEW_BY_ID = "SELECT id, user_id, added_on, review_text, food_id, rating FROM Review WHERE id = ?";
static const char *REVIEWS_BY_USERID = "SELECT id, user_id, added_on, review_text, food_id, rating FROM Review WHERE user_id = ?";
static const char *REVIEWS_BY_FOODID = "SELECT id, user_id, added_on, review_text, food_id, rating FROM Review WHERE food_id = ?";

void ReviewDAOMySQL::addReview(Review &review, int &newId)
{
    MySQLManager::ConnectionHolder ch(MySQLManager::getInstance());
    std::unique_ptr<sql::PreparedStatement> ps(ch.conn->prepareStatement(ADD_REVIEW));

    ps->setInt(1, review.getUserId());
    ps->setString(2, review.getAddedOn());
    ps->setString(3, review.getReviewText());
    ps->setInt(4, review.getFoodId());
    ps->setDouble(5, review.getRating());

    ps->execute();

    newId = MySQLManager::lastGeneratedId(ch.conn);
}

void ReviewDAOMySQL::deleteReview(Review &review)
{
    MySQLManager::ConnectionHolder ch(MySQLManager::getInstance());
    std::unique_ptr<sql::PreparedStatement> ps(ch.conn->prepareStatement(DELETE_REVIEW));

    ps->setInt(1, review.getId());

    ps->execute();
}

void ReviewDAOMySQL::updateReview(const Review &review)
{
    MySQLManager::ConnectionHolder ch(MySQLManager::getInstance());
    std::unique_ptr<sql::PreparedStatement> ps(ch.conn->prepareStatement(UPDATE_REVIEW));

    ps->setInt(1, review.getUserId());
    ps->setString(2, review.getAddedOn());
    ps->setString(3, review.getReviewText());
    ps->setInt(4, review.getFoodId());
    ps->setDouble(5, review.getRating());
    ps->setInt(6, review.getId());

    ps->executeUpdate();
}

Review ReviewDAOMySQL::getReviewById(const int &id)
{
    MySQLManager::ConnectionHolder ch(MySQLManager::getInstance());
    std::unique_ptr<sql::PreparedStatement> ps(ch.conn->prepareStatement(REVIEW_BY_ID));

    ps->setInt(1, id);

    std::unique_ptr<sql::ResultSet> res(ps->executeQuery());

    if(!res->next())
    {
        throw DAOException("Review not found!");
    }

    return Review(res->getInt(1), res->getInt(2), res->getString(3), res->getString(4), res->getInt(5), res->getDouble(6));
}

std::vector<Review> ReviewDAOMySQL::getReviewByUserId(const int &userId)
{
    MySQLManager::ConnectionHolder ch(MySQLManager::getInstance());
    std::unique_ptr<sql::PreparedStatement> ps(ch.conn->prepareStatement(REVIEWS_BY_USERID));

    ps->setInt(1, userId);

    std::unique_ptr<sql::ResultSet> res(ps->executeQuery());

    std::vector<Review> reviews;
    while(res->next())
    {
        reviews.push_back(Review(res->getInt(1), res->getInt(2), res->getString(3), res->getString(4), res->getInt(5), res->getDouble(6)));
    }

    return reviews;
}

std::vector<Review> ReviewDAOMySQL::getReviewByFoodId(const int &foodId)
{
    MySQLManager::ConnectionHolder ch(MySQLManager::getInstance());
    std::unique_ptr<sql::PreparedStatement> ps(ch.conn->prepareStatement(REVIEWS_BY_FOODID));

    ps->setInt(1, foodId);

    std::unique_ptr<sql::ResultSet> res(ps->executeQuery());

    std::vector<Review> reviews;
    while(res->next())
    {
        reviews.push_back(Review(res->getInt(1), res->getInt(2), res->getString(3), res->getString(4), res->getInt(5), res->getDouble(6)));
    }

    return reviews;
}
