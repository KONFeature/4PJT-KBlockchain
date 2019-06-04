"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
var NodeService = /** @class */ (function () {
    function NodeService(http) {
        this.http = http;
    }
    NodeService.prototype.getNode = function () {
        return this.http.get("http://10.17.16.208:8070/network/nodes");
    };
    return NodeService;
}());
exports.NodeService = NodeService;
//# sourceMappingURL=NodeService.js.map