

var express = require('express')
  , routes = require('./routes')
  , fs = require('fs')
  , MongoStore = require('connect-mongo')(express)
  , User = require('./models/User.js');

var app = module.exports = express.createServer();  

var sessionStore = new MongoStore ({ url: 'mongodb://test_usr:test_pwd@ds033699.mongolab.com:33699/shamanic_db', db: 'shamanic_db' }, function(e) {
	var cookieParser = express.cookieParser('something');
	app.use(cookieParser);
	
	app.use(express.session({ secret: '1q2w3e4r5t6y7u8i9o0p', store: sessionStore }));
	
	app.listen();
});

// Configuration

app.configure(function(){
  app.set('views', __dirname + '/views');
  app.set('view engine', 'jade');
  app.use(express.bodyParser());
  app.use(express.cookieParser());
  app.use(express.methodOverride());
  app.use(app.router);
  app.use(express.static(__dirname + '/public'));
});

app.configure('development', function(){
  app.use(express.errorHandler({ dumpExceptions: true, showStack: true }));
});

app.configure('production', function(){
  app.use(express.errorHandler());
});

// Routes
app.get('/', routes.index);

app.get('/form', function(req, res) {
  fs.readFile('./form.html', function(error, content) {
    if (error) {
      res.writeHead(500);
      res.end();
    }
    else {
      res.writeHead(200, { 'Content-Type': 'text/html' });
      res.end(content, 'utf-8');
    }
  });
});

app.post('/signup', function(req, res) {
  var username = req.body.username;
  var password = req.body.password;
  User.addUser(username, password, function(err, user) {
    if (err) throw err;
    res.redirect('/form');
  });  
});

app.get('/signup', function(req, res) {
	fs.readFile('./signup.html', function(error, content) {
		if (error) {
			res.writeHead(500);
			res.end();
		}
		else {
			res.writeHead(200, { 'Content-Type': 'text/html' });
			res.end(content, 'utf-8');
		}
	});
});

app.listen(3000);

console.log("Express server listening on port %d in %s mode", app.address().port, app.settings.env);
