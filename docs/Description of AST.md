# Description of AST

In code
```
$my_age = 2024 - 2000;
print($my_age)
```

AST
```JSON
{
    "type": "prog",
    "prog": [
        {
            "type": "assignment",
            "target": {
                "type": "variable",
                "name": "my_age"
            },
            "value": {
                "type": "operation",
                "operator": "-",
                "left": {
                    "type": "num",
                    "value": 2024
                },
                "right": {
                    "type": "num",
                    "value": 2000
                }
            }
        },
        {
            "type": "call",
            "func": {
              "type": "var",
              "name": "print",
            },
          "args": {
            {
              "type": "var",
              "value": "my_age"
            }
          }
        }
    ]
}
```