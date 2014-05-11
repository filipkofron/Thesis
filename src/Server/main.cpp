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

#include <jsoncpp/json/writer.h>
#include "Network/Server.hpp"
#include "Util/Log.hpp"

#include <csignal>
#include <unistd.h>

using namespace std;

/*!
 * \brief json_exception_test test for exceptions from JSON, if crash occurs, the JSON doesn't provide exception throwing
 */
void json_exception_test()
{
    Log::info("Checking whether the JSON library throws exceptions, thus catching them is possible.");
    Json::Value test;
    test = "fuck";
    try
    {
        test["gtfo"] = "fuck";
    }
    catch(std::runtime_error &e)
    {
    }

    try
    {
        test["gtfo"].asInt();
    }
    catch(std::runtime_error &e)
    {
    }
    Log::info("JSON library passed the test.");
}

static Server server;

/*!
 * \brief onSigInt called on a signal
 * \param s the signal number
 */
static void onSigInt(int s)
{
    Log::info("Kill signal received! => Stopping server.");
    server.setStopped(true);
}

/*!
 * \brief initSignals initializes handling of signals
 */
static void initSignals()
{
    struct sigaction sigIntHandler;

    sigIntHandler.sa_handler = onSigInt;
    sigemptyset(&sigIntHandler.sa_mask);
    sigIntHandler.sa_flags = 0;

    sigaction(SIGINT, &sigIntHandler, NULL);
    signal(SIGPIPE, SIG_IGN);
}


/*!
 * \brief main initializes and executes the server
 * \return 0 all the time
 */
int main()
{
    initSignals();

    Log::info("> [Food inventory server]");
    Log::info("> Author: Filip Kofron (filip.kofron.cz@gmail.com)");
    Log::info(std::string("> Build: ") + std::string(__DATE__) + std::string(" ") + std::string(__TIME__));
    Log::info("====================================================");
    json_exception_test();

    server.initialize();
    server.run();

    return 0;
}
