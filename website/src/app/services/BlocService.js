"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
var http_1 = require("@angular/common/http");
var BlocService = /** @class */ (function () {
    function BlocService(http) {
        this.http = http;
    }
    BlocService.prototype.getBlock = function () {
        return this.http.post("http://10.17.16.208:8070/blockchain/blocks", null).subscribe({
            next: function (response) { console.log(response); },
            error: function (err) { console.error('Error: ' + err); },
            complete: function () { console.log('Completed'); }
        });
    };
    BlocService.prototype.getBlockDetail = function (id) {
        var params = new http_1.HttpParams().set("id", id.toString());
        return this.http.get("http://10.17.16.208:8070/blockchain/blocks", { params: params });
    };
    return BlocService;
}());
exports.BlocService = BlocService;
//# sourceMappingURL=BlocService.js.map