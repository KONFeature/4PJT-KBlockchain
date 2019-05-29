"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
var http_1 = require("@angular/common/http");
var WalletService = /** @class */ (function () {
    function WalletService(http) {
        this.http = http;
    }
    WalletService.prototype.createWallet = function (name, mail, token) {
        var params = new http_1.HttpParams().set("name", name).set("mail", mail).set("token", token);
        return this.http.post("http://10.17.16.208:8070/wallet/create", { params: params });
    };
    WalletService.prototype.walletLoad = function () {
        return this.http.get("http://10.17.16.208:8070/wallet/load");
    };
    return WalletService;
}());
exports.WalletService = WalletService;
//# sourceMappingURL=WalletService.js.map