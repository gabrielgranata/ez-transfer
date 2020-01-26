let express = require('express');

let app = express();

let algosdk = require('algosdk');


app.get('/', function() {
    console.log("Hello World");
})

createAccount();

function createAccount() {
    let account = algosdk.generateAccount();
    let address = account.addr;
    let sk = account.sk;
    let mnemonic = algosdk.secretKeyToMnemonic(sk);

    console.log(address);
    console.log(mnemonic);

    
}

app.listen(3000);