var express = require('express');
var router = express.Router();

var commonmark = require('commonmark');

const MongoClient = require('mongodb').MongoClient;
const url = 'mongodb://localhost:27017/';
const mydb = 'BlogServer';
const col = 'Posts'

let db = null;

const num = 5

MongoClient.connect(url, function(err, client) {
    if (err) throw err;
    console.log("Successfully connected to MongoDB!");
    db = client.db(mydb);
});

const getAllBlogs = function(db, username, callback) {
    db.collection(col).find({"username": username}).toArray(function(err, posts) {
        callback(posts);
    });
}

const getOneBlog = function(db, username, postid, callback) {
    db.collection(col).findOne({ "username": username, "postid": parseInt(postid) })
    .then(callback);
}

const markdownRender = function(post) {
    var reader = new commonmark.Parser();
    var writer = new commonmark.HtmlRenderer();
    var parsed = reader.parse(post.title);
    renderedTitle = writer.render(parsed);
    parsed = reader.parse(post.body);
    renderedBody = writer.render(parsed);
    return [renderedTitle, renderedBody]
}

/* GET blog */
router.get('/:username/:postid', function(req, res, next) {
    getOneBlog(db, req.params.username, req.params.postid, function(post) {
        if(post === null)
            res.render('blog',  { title: "", body: "" });
        else {
            rendered = markdownRender(post);
            res.render('blog',  { title: renderedTitle, body: renderedBody });
        }
    });
});

/* GET blogs */
router.get('/:username', function(req, res, next) {
    getAllBlogs(db, req.params.username, function(posts) {
        let startid = req.query.start;
        if(startid === undefined)
            startid = 1;
        let i = 0;
        for(i = 0; i < posts.length; i++)
            if(posts[i].postid >= startid)
                break;
        let hasNext = false;
        let nextid = -1;
        if(i + num < posts.length) {
            hasNext = true;
            nextid = posts[i+num].postid;
        }
        let slicedPosts = posts.slice(i, i+num);
        let renderedPosts = new Array();
        for (let j = 0; j < slicedPosts.length; j++){
            renderedPosts[j] = markdownRender(slicedPosts[j]);
        }
        res.render('blogs',  { renderedPosts: renderedPosts, hasNext: hasNext, nextid: nextid });
  });
});


module.exports = router;
