#include <iostream>

using namespace std;

int main()
{
    cout << "Hello World!" << endl;
    struct test_s
    {
        int a;
        float b;
    };

    test_s test[] = {{1, 3.14159f}};

    for(test_s val : test)
    {
        cout << "val: " << val.a << ", " << val.b << endl;
    }
    return 0;
}

