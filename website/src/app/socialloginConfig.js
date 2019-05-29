"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
var angular5_social_login_1 = require("angular5-social-login");
function getAuthServiceConfigs() {
    var config = new angular5_social_login_1.AuthServiceConfig([
        {
            id: angular5_social_login_1.FacebookLoginProvider.PROVIDER_ID,
            provider: new angular5_social_login_1.FacebookLoginProvider('2347129585338818')
        }
    ]);
    return config;
}
exports.getAuthServiceConfigs = getAuthServiceConfigs;
//# sourceMappingURL=socialloginConfig.js.map