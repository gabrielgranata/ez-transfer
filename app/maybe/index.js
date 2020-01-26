let express = require('express');
let firebase = require('firebase');
let app = express();

let auth;
let db;

let algosdk = require('algosdk');

const firebaseConfig = {
    apiKey: "AIzaSyCWphuDGe30HLOzDHJgOLX8B6ALXMcLG0k",
    authDomain: "deltahacks6-d837e.firebaseapp.com",
    databaseURL: "https://deltahacks6-d837e.firebaseio.com",
    projectId: "deltahacks6-d837e",
    storageBucket: "deltahacks6-d837e.appspot.com",
    messagingSenderId: "713129899445",
    appId: "1:713129899445:web:15a11ae4d8122620c995fc"
};

init();

app.get('/', async function (req, res) {

    await signin(req.query.login, req.query.password);
})


app.get('/transaction', async function (req, res) {

    let currentUser = auth.currentUser;
    let uid = currentUser.uid;

    let userRef = await db.collection('users').doc(uid);
    let getDoc = await userRef.get()
                    .then(doc => {
                        let data = doc.data();
                        mnemonic = data.mnemonic;
                    })

    let to = req.query.toAddress;
    let amount = req.query.amount;
    await makeTransaction(mnemonic, to, amount);
})

function init() {

    firebase.initializeApp(firebaseConfig);
    auth = firebase.auth();
    db = firebase.firestore();

}


async function signin(email, password) {
    let account = algosdk.generateAccount();
    let address = account.addr;
    let sk = account.sk;
    let mnemonic = algosdk.secretKeyToMnemonic(sk);

    console.log(address);
    console.log(mnemonic);

    await auth.signInWithEmailAndPassword(email, password).catch(function (error) {
        if (error) {
            console.log(error);
        } else {
            console.log("Success");
        }
    });

    let user = auth.currentUser;

    let uid = user.uid;

    await db.collection('users').doc(uid).set({
        uid: uid,
        email: email,
        address: address,
        mnemonic: mnemonic
    }).catch(function (error) {
        console.log(error);
    });


}

async function makeTransaction(from, to, amount) {

    const atoken = "ef920e2e7e002953f4b29a8af720efe8e4ecc75ff102b165e0472834b25832c1";
    const aserver = "http://hackathon.algodev.network";
    const aport = 9100;

    //Recover the account 
    var mnemonic = "you mnemonic string";
    var recoveredAccount = algosdk.mnemonicToSecretKey(from);

    amount = parseInt(amount)
    //instantiate the algod wrapper
    let algodclient = new algosdk.Algod(atoken, aserver, aport);
    (async () => {
        //Get the relevant params from the algod
        let params = await algodclient.getTransactionParams();
        let endRound = params.lastRound + parseInt(1000);
        //create a transaction
        //note that the closeRemainderTo parameter is commented out
        //This parameter will clear the remaining funds in an account and 
        //send to the specified account if main transaction commits
        let txn = {
            "from": recoveredAccount.addr,
            "to": to,
            "fee": 1000,
            "amount": amount,
            "firstRound": params.lastRound,
            "lastRound": endRound,
            "genesisID": params.genesisID,
            "genesisHash": params.genesishashb64,
            "note": new Uint8Array(0),
            //"closeRemainderTo": "IDUTJEUIEVSMXTU4LGTJWZ2UE2E6TIODUKU6UW3FU3UKIQQ77RLUBBBFLA"
        };
        //sign the transaction
        let signedTxn = algosdk.signTransaction(txn, recoveredAccount.sk);
        //submit the transaction
        let tx = (await algodclient.sendRawTransaction(signedTxn.blob));
        console.log("Transaction : " + tx.txId);

    })().catch(e => {
        console.log(e);
    });


}
app.listen(3000);
