# Generate test data for Products, using faker.js

## Installation

```bash
$ cd tareaMock
$ npm install
```

## Usage

```bash
$ node index.js -u -n 4 -s 2
[
  {
    "name": "Salad0",
    "price": 634,
    "stock": 0
  },
  {
    "name": "Shirt1",
    "price": 469,
    "stock": 0
  }
]
```
### Options
* -u, --unique: Unique object names.
* -n, --number [count]: Will generate [count] objects.
* -s, --space [spaces]: Number of space characters to use as white space. If not specified, will default to 0.
* -o, --output [file]: Output results to this specified file. If not specified, will output to stdout.
