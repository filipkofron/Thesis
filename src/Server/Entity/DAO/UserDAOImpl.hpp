#include "UserDAO.hpp"

class UserDAOImpl : public virtual UserDAO
{
public:
    UserDAOImpl() {}
    virtual ~UserDAOImpl() {}


    virtual void addUser(User &user, int &newId) override;
    virtual void deleteUser(User &user) override;
    virtual void updateUser(const User &user) override;
    virtual User getUserByUsername(const std::string &username) override;
    virtual User getUserById(const int &id) override;
};
