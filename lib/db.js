// Consider renaming this to schema.js?

var mongoose = require('mongoose');
var Schema = mongoose.Schema;

module.exports.mongoose = mongoose;
module.exports.Schema = Schema;

// Connect to cloud database
var username = "test_usr";
var password = "test_pwd";
var address = '@ds033699.mongolab.com:33699/shamanic_db';
connect();

// Connect to mongo
function connect() {
  var url = 'mongodb://' + username + ':' + password + address;
  mongoose.connect(url);
}

// TODO: Session tracking

function disconnect() {mongoose.disconnect()}