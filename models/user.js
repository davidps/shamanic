var db = require('../lib/db');

var UserSchema = new db.Schema({
    username : {type: String, unique: true}
  , email: {type: String, unique: false}
  , password : String
});

var MyUser = db.mongoose.model('User', UserSchema);

// Exports
module.exports.addUser = addUser;

// Add user to database

function addUser(username, email, password, callback) {
  var instance = new MyUser();
  instance.username = username;
  instance.email = email;
  instance.password = password;
  instance.save(function (err) {
    if (err) {
      callback(err);
    }
    else {
      callback(null, instance);
    }
  });
}