var processArgs = require('minimist');
var faker = require('faker');
var fs = require('fs');

var args = processArgs(process.argv.slice(2), {alias: {'unique': 'u', 'number': 'n', 'output': 'o', 'space': 's'}});
var out = [];
var n = args.number ? args.number : 10;
var space = args.space ? args.space : null;
var name;

// console.log(args);

for (var i = 0; i < n; i++) {
    name = faker.commerce.product();
    if (args.unique) {
        name += i;
    }
    out.push({
        name: name,
        price: faker.random.number(1000, 50000),
        stock: faker.random.number(0, 150)
    });
}

if (args.output) {
    var outputFile = args.output;
    fs.writeFile(outputFile, JSON.stringify(out, null, space), function (err) {
        if (err) {
            console.log(err);
        } else {
            console.log("JSON saved to " + outputFile);
        }
    });
} else {
    console.log(JSON.stringify(out, null, space));
}
