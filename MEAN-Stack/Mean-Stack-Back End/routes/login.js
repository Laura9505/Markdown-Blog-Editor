var express = require('express');
var router = express.Router();

var commonmark = require('commonmark');
var bcrypt = require('bcrypt');
var jwt = require('jsonwebtoken');
const secret = 'C-UFRaksvPKhx1txJYFcut3QGxsafPmwCY6SCly3G6c';

const MongoClient = require('mongodb').MongoClient;
const url = 'mongodb://localhost:27017/';
const mydb = 'BlogServer';
const col = 'Users'

let db = null;


MongoClient.connect(url, function(err, client) {
    if (err) throw err;
    console.log("Successfully connected to MongoDB!");
    db = client.db(mydb);
});


const getUser = function(db, username, callback) {
    db.collection(col).findOne({ "username": username })
    .then(callback);
}

router.get('/', function(req, res, next) {
    let username = req.query.username;
    let password = req.query.password;
    let redirect = req.query.redirect;

    res.status(200).render('login', { username: username, redirect: redirect });
});

router.post('/', function(req, res, next) {
    let username = req.body.username;
    let password = req.body.password;
    let redirect = req.body.redirect;

    if(username == null) {
        res.render('login', { username: username, redirect: redirect });
    } else {
        getUser(db, username, function(user) {
            console.log(user);
            if(user === null) {
                res.status(401).render('login-fail', { username: username, redirect: redirect });
            }
            else {
                let hash = user.password;
                bcrypt.compare(password, hash, function(err, result) {
                    console.log(result);
                    if(!result) {  // password wrong or empty
                        res.status(401).render('login-fail', { username: username, redirect: redirect });
                    }
                    else {
                        let expiration = Math.floor(Date.now() / 1000) + 7200;
                        let token = jwt.sign({ exp: expiration, usr: username }, secret);
                        console.log(token);
                        res.cookie('jwt', token);
                        if(!redirect) {
                            // res.sendStatus(200);
                            res.status(200).render('login-success');
                        }
                        else {
                            res.status(200).redirect(redirect);
                        }
                    }
                });
            }
        });
    }
});

module.exports = router;