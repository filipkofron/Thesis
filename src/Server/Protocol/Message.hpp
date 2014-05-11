/*
Copyright (C) 2014 Filip Kofron (filip.kofron.cz@gmail.com)

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
*/

#ifndef _MESSAGE_HPP_
#define _MESSAGE_HPP_

class Message;

#include <jsoncpp/json/reader.h>
#include <jsoncpp/json/value.h>
#include "../Handler/Handler.hpp"

/*!
 * The Message is an abstract class providing an interface to work with a message.
 *
 * The class is supposed to be able to convert to JSON representation and back from it.
 * All child messages must be registered in the dejsonize static method, so the classes can be
 * deserialized from the network./
 */
class Message
{
protected:
    /*!
     * \brief Initializes message.
     */
    Message();

    /*!
     * \brief _dejsonize dejsonizes the message from the given content object, which must contain
     * all the message specific atributes
     * \param content the message specific content of atributes
     */
    virtual void _dejsonize(Json::Value &content) = 0;

    /*!
     * \brief _jsonize jsonizes this message to the given content object, which must set all
     * the message specific atributes directly inside it.
     * \param content
     */
    virtual void _jsonize(Json::Value &content) = 0;

public:
    /*!
     * \brief getHeader retrieves the message header, the message name.
     * \return the message header
     */
    virtual std::string getHeader() = 0;

    /*!
     * \brief ~Message made virtual so all child message destructors can be called
     */
    virtual ~Message();

    /*!
     * \brief dejsonize dejsonizes a given message in the json format, the method will itself
     * choose the correct child message and dejsonize by it.
     * \param root the object containing the message header and content
     * \return an instance of message or null on error
     *
     * An exception can be raised from the JSONCpp library.
     */
    static Message *dejsonize(Json::Value &root);
    virtual void jsonize(Json::Value &root) final;
    virtual Handler *createHandler() = 0;
};

#endif
