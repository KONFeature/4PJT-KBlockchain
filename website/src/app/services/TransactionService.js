"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
var TransactionService = /** @class */ (function () {
    function TransactionService(http) {
        this.http = http;
    }
    TransactionService.prototype.getTransactions = function () {
        return this.http.get("http://10.17.16.208:8070/blockchain/transactions").subscribe({
            next: function (response) { console.log(response); },
            error: function (err) { console.error('Error: ' + err); },
            complete: function () { console.log('Completed'); }
        });
    };
    TransactionService.prototype.getTransactionPool = function () {
        return this.http.get("http://10.17.16.208:8070/blockchain/pool").subscribe({
            next: function (response) { console.log(response); },
            error: function (err) { console.error('Error: ' + err); },
            complete: function () { console.log('Completed'); }
        });
    };
    return TransactionService;
}());
exports.TransactionService = TransactionService;
//# sourceMappingURL=TransactionService.js.map