//externalize mappings.js which we can import.

'use strict';

var fs = require('fs'),
	db = require('../lib/db');

var MappingSchema = new db.Schema({
	alias : {type: String, unique: true}
});

var siteViews = db.mongoose.model('Mapping', MappingSchema);
	
var data = {
	g: 'http://www.google.com',
	map: function(req, res) {
	fs.readFile('../map.html')}
};

var mappings = {
	get: function(alias, callback) {
		db.mappings.findOne({ alias: alias }, function(err, mapping) {
			if (err || !mapping) {
				return callback(new Error('Alias not found.'));
			}
			callback(null, mapping.url);
		});
	},
	
	create: function(alias, url, callback) {
		db.mappings.insert({ alias: alias, url: url }, callback);
	},
	
	list: function (callback) {
		db.mappings.find({}).sort({alias: 1}).exec(callback);
	}
};

module.exports = mappings;