#ifndef _CONFIGURATOR_HPP_
#define _CONFIGURATOR_HPP_

class Configurator;

#include <jsoncpp/json/value.h>
#include <jsoncpp/json/reader.h>

class Configurator
{
private:
    Json::Value root;
public:
    Configurator();
    static Configurator *getInstance();
    void reload();

    std::string getMySQLServerAddress();
    std::string getMySQLServerUsername();
    std::string getMySQLServerPassword();
    std::string getMySQLServerDatabase();
    std::string getImageDir();
    int getNetworkClientMaxBuffer();
    int getNetworkServerListenPort();
    int getNetworkServerBackLog();
    int getNetworkServerMaxClients();
};

#define DEFAULT_JSON_DOCUMENT \
        "{ " \
        "\"database\" : {" \
      "\"mysql\" : {" \
         "\"address\" : \"tcp://localhost:3306/\"," \
         "\"password\" : \"toor\"," \
         "\"username\" : \"root\"," \
         "\"database\" : \"mydb\"" \
      "}" \
   "}," \
    "\"network\" : {" \
          "\"client\" : {" \
    "\"max_buffer\" : 131072" \
            "}," \
            "\"server\" : {" \
            "\"listen_port\" : 4040," \
            "\"back_log\" : 32," \
            "\"max_clients\" : 256" \
"}" \
"    }" \
"}"

#endif
