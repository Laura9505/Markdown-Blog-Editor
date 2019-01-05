var express = require('express');
var router = express.Router();

var commonmark = require('commonmark');
var bcrypt = require('bcrypt');
var jwt = require('jsonwebtoken');
const secret = 'C-UFRaksvPKhx1txJYFcut3QGxsafPmwCY6SCly3G6c';

const MongoClient = require('mongodb').MongoClient;
const url = 'mongodb://localhost:27017/';
const mydb = 'BlogServer';
const collName = 'Posts'

let db = null;


MongoClient.connect(url, function(err, client) {
    if (err) throw err;
    console.log("Successfully connected to MongoDB!");
    db = client.db(mydb);
});

const getPosts = function(db, username, callback) {
    db.collection(collName).find({"username": username}).toArray(function(err, posts) {
        callback(posts);
    });
}

const getPost = function(db, username, postid, callback) {
    db.collection(collName).findOne({ "username": username, "postid": parseInt(postid) })
    .then(callback);
}

const newPost = function(db, post, callback) {
    db.collection(collName).insertOne(post, callback);
}

const updatePost = function(db, post, callback) {
    db.collection(collName).updateOne({"username": post.username, "postid": parseInt(post.postid)}, {$set: post}, callback);
}

const deletePost = function(db, username, postid, callback) {
    db.collection(collName).deleteOne({ "username": username, "postid": parseInt(postid) }, callback);
}



router.use('/:username', function(req, res, next) {
    let username = req.params.username;
    let token = req.cookies.jwt;
    if (!token)
        res.sendStatus(401);  // Unauthorized
    else {
        jwt.verify(token, secret, function(err, decoded) {
            if(err || username != decoded.usr) {
                console.log(err);
                res.sendStatus(401);  // Unauthorized
            }
            else {
                console.log(username);
                console.log(decoded);
                next();
            }
        });
    }
});

// GET /api/:username: return all blog posts by username
router.get('/:username', function(req, res) {
    getPosts(db, req.params.username, function(posts) {
        res.status(200).json(posts);     // 200 (OK)
    });
});

// GET /api/:username/:postid: return the blog post with postid by username
router.get('/:username/:postid', function(req, res) {
    getPost(db, req.params.username, req.params.postid, function(post) {
        if(post === null)
            res.sendStatus(404);        // 404 (Not found)
        else
            res.status(200).json(post);     // 200 (OK)
  });
});

// POST /api/:username/:postid: insert a new blog post with username, postid, title, and body from the request
router.post('/:username/:postid', function(req, res) {
    let username = req.params.username;
    let postid = req.params.postid;
    getPost(db, username, postid, function(post){
        if (post != null)
            res.sendStatus(400);        // 400 (Bad request)
        else {
            let post = new Object();
            let currentTime = new Date();
            post.postid = parseInt(postid);
            post.username = username;
            post.created = currentTime.getTime();
            post.modified = currentTime.getTime();
            post.title = req.body.title;
            post.body = req.body.body;
            newPost(db, post, function(err, result) {
                res.sendStatus(201);    // 201 (Created)
            });
        }
    });
});

// PUT /api/:username/:postid: update the existing blog post with postid by username with the title and body values from the request
router.put('/:username/:postid', function(req, res) {
    let username = req.params.username;
    let postid = req.params.postid;
    getPost(db, username, postid, function(post) {
        if(post === null)
            res.sendStatus(400);        // 400 (Bad request)
        else {
            let currentTime = new Date();
            post.modified = currentTime.getTime();
            post.title = req.body.title;
            post.body = req.body.body;
            updatePost(db, post, function(err, result) {
                res.sendStatus(200);    // 200 (OK)
            });
        }
    });
});

// DELETE /api/:username/:postid: delete the existing blog post with postid by username from the database
router.delete('/:username/:postid', function(req, res) {
    let username = req.params.username;
    let postid = req.params.postid;
    getPost(db, username, postid, function(post) {
        if(post === null)
            res.sendStatus(400);        // 400 (Bad request)
        else {
            deletePost(db, username, postid, function(err, result) {
                res.sendStatus(204);    // 204 (No content)
            });
        }
    });
});

module.exports = router;