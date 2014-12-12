var db = require('../lib/db');

var TagSchema = new db.Schema({
    user_id : {type: String, unique: true}
  , lat : String
  , lng : String
  , ele : String
  , timestamp : Integer
  , expires_yn : String
  , home_yn : String
  , url : String
})

var MyTag = db.mongoose.model('Tag', TagSchema);

// Exports
module.exports.addTag = addTag;

// Add user to database
function addTag([params], callback) {
  var instance = new MyTag();
  var params = [
  instance.lat = lat,
  instance.lng = lng,
  instance.ele = ele,
  instance.timestamp = timestamp,
  instance.expires_yn = expires_yn,
  instance.home_yn = home_yn,
  instance.url = url,
  instance.password = password,
  ];
  instance.save(function (err) {
    if (err) {
      callback(err);
    }
    else {
      callback(null, instance);
    }
  });
}