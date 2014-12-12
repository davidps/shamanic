//var myApp = angular.module("myApp", ["shamanicApp"]);

/**
 * Module dependencies.
 */

var express = require('express')
  , serveStatic = require('serve-static')
  , http = require('http')
  , session = require('express-session')
  , MongoStore = require('connect-mongo')(session)
  , routes = require('./routes')
  , mappings = require('./models/mappings')
  , fs = require('fs')
  , path = require('path')
  , user = require('./models/user')
  , bodyParser = require('body-parser')
  , cookieParser = require('cookie-parser')
  , methodOverride = require('method-override')
  , errorHandler = require('errorhandler')
  , ejs = require('ejs');
  //, shamanicApp = require('./shamanicApp');
  
var env = process.env.NODE_ENV || 'development';
var app = express();
var sessionStore = new MongoStore ({ url: 'mongodb://test_usr:test_pwd@ds033699.mongolab.com:33699/shamanic_db', db: 'shamanic_db' }, function(e) {
	//var cookieParser = cookieParser('something');
	app.use(cookieParser);
	
	app.use(session({ secret: '1q2w3e4r5t6y7u8i9o0p', saveUninitialized: true, resave: true, store: sessionStore }));
	
	app.listen();
});

// Configuration

app.set('views', path.join(__dirname, '/views'));
app.set('view engine', 'ejs');
app.use(bodyParser());
app.use(cookieParser());
app.use(methodOverride());
//app.use(app.router);
app.use(express.static(path.join(__dirname, '/public')));

if ('development' == env) {
    app.use(errorHandler({ dumpExceptions: true, showStack: true }));
} else {
	app.use(errorHandler());
};


// Custom Directives


// Routes

app.get('/', function(req, res) {
	mappings.list(function (err, documents) {
		res.render('index', {
		mappings: documents
		});
	});
});

app.get('/form', function(req, res) {
	fs.readFile('./views/form.html', function(error, content) {
		if (error) {
			console.log(error);
			res.writeHead(500);
			res.end();
		}
		else {
			res.writeHead(200, { 'Content-type': 'text/html' });
			res.end(content, 'utf-8');
		}
	});
});

app.get('/index', function(req, res) {
	fs.readFile('./index.ejs', function(error, content) {
		if (error) {
			console.log(error);
			res.writeHead(500);
			res.end();
		}
		else {
			res.writeHead(200, { 'Content-type': 'text/html' });
			res.end(content, 'utf-8');
		}
	});
});

/*  
app.get('/form', function(req, res) {
	res.render('form');
});
 */

// TODO: Route to return to landing page / index

app.post('/signup', function(req, res) {
  var username = req.body.username;
  var email = req.body.email;
  var password = req.body.password;
  console.log('un=' + req.body.username + ', em=' + req.body.email + ', pw=' + req.body.password);
  user.addUser(username, email, password, function(err, user) {
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

 app.get('/map', function(req, res) {
	fs.readFile('./map.html', function(error, content) {
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

app.get('/:alias', function (req, res) {
	mappings.get(req.params.alias, function(err, mapping) {

	if(err) {
		res.writeHead(404);
		return res.end();
	} 
		res.writeHead(302, { location: mapping });
		res.end();
	});
});

app.get('*', function(req, res, next) {
  var err = new Error();
  err.status = 404;
  next(err);
});
 
// handling 404 errors
app.use(function(err, req, res, next) {
  if(err.status !== 404) {
    return next();
  }
 
  res.send(err.message || '** shit man, i don\'t know what to tell you. back button, i guess? **');
});

/* app.factory("errors", function($rootScope){
    return {
        catch: function(message){
            return function(reason){
                $rootScope.addError({message: message, reason: reason})
            };
        }
    };
}); */

var server = app.listen(3003, function() {
console.log("Express server listening on port %d in %s mode", server.address().port, app.settings.env);
});
