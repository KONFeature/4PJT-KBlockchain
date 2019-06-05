(window["webpackJsonp"] = window["webpackJsonp"] || []).push([["main"],{

/***/ "./node_modules/raw-loader/index.js!./src/app/app.component.html":
/*!**************************************************************!*\
  !*** ./node_modules/raw-loader!./src/app/app.component.html ***!
  \**************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "<body>\r\n  <app-nav-menu style=\"padding:0px 0px 0px 0px\"></app-nav-menu>\r\n  <div class=\"container col-lg-12\" style=\"padding:0px 0px 0px 0px\">\r\n    <router-outlet></router-outlet>\r\n  </div>\r\n</body>\r\n"

/***/ }),

/***/ "./node_modules/raw-loader/index.js!./src/app/block-modal/block-modal.component.html":
/*!**********************************************************************************!*\
  !*** ./node_modules/raw-loader!./src/app/block-modal/block-modal.component.html ***!
  \**********************************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "<p>\n  block-modal works!\n</p>\n"

/***/ }),

/***/ "./node_modules/raw-loader/index.js!./src/app/fetch-data/fetch-data.component.html":
/*!********************************************************************************!*\
  !*** ./node_modules/raw-loader!./src/app/fetch-data/fetch-data.component.html ***!
  \********************************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "<h1>Weather forecast</h1>\r\n\r\n<p>This component demonstrates fetching data from the server.</p>\r\n\r\n<p *ngIf=\"!forecasts\"><em>Loading...</em></p>\r\n\r\n<table class='table table-striped' *ngIf=\"forecasts\">\r\n  <thead>\r\n    <tr>\r\n      <th>Date</th>\r\n      <th>Temp. (C)</th>\r\n      <th>Temp. (F)</th>\r\n      <th>Summary</th>\r\n    </tr>\r\n  </thead>\r\n  <tbody>\r\n    <tr *ngFor=\"let forecast of forecasts\">\r\n      <td>{{ forecast.dateFormatted }}</td>\r\n      <td>{{ forecast.temperatureC }}</td>\r\n      <td>{{ forecast.temperatureF }}</td>\r\n      <td>{{ forecast.summary }}</td>\r\n    </tr>\r\n  </tbody>\r\n</table>\r\n"

/***/ }),

/***/ "./node_modules/raw-loader/index.js!./src/app/home/home.component.html":
/*!********************************************************************!*\
  !*** ./node_modules/raw-loader!./src/app/home/home.component.html ***!
  \********************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "\r\n<div class=\"col-lg-12\" style=\"padding:0px\">\r\n  <div class=\"row col-lg-12 mb-4\">\r\n    <div class=\"col-lg-2\"></div>\r\n    <div class=\"col-lg-8 text-center border-bottom\">\r\n      <h1 class=\"text-info\" style=\"font-weight: 800; font-size:2em; height:40px; line-height:15px\">SUPCOIN</h1>\r\n    </div>\r\n    <div class=\"col-lg-2\"></div>\r\n  </div>\r\n  <div class=\"row col-lg-12 mb-4 \">\r\n    <div class=\"col-lg-2 \"></div>\r\n    <div class=\"col-md-2 mb-1 text-center\">\r\n      <div class=\"card shadow p-3  bg-white rounded\" style=\"width: 18rem;\">\r\n        <div class=\"card-body text-center\" style=\"padding:15px;\">\r\n          <h5 class=\"card-title\">Blocs</h5>\r\n          <h5 class=\"card-title\">{{totalBlocks}}</h5>\r\n        </div>\r\n      </div>\r\n    </div>\r\n    <div class=\"col-md-2 mb-1 text-center\">\r\n      <div class=\"card shadow p-3  bg-white rounded\" style=\"width: 18rem;\">\r\n        <div class=\"card-body text-center\" style=\"padding:15px;\">\r\n          <h5 class=\"card-title\">Transactions</h5>\r\n          <h5 class=\"card-title\">{{totalTransaction}}</h5>\r\n        </div>\r\n      </div>\r\n    </div>\r\n    <div class=\"col-md-2 mb-1 text-center\">\r\n      <div class=\"card shadow p-3  bg-white rounded\" style=\"width: 18rem;\">\r\n        <div class=\"card-body text-center\" style=\"padding:15px;\">\r\n          <h5 class=\"card-title\">Transactions minées</h5>\r\n          <h5 class=\"card-title\">{{totalTransactionMined}}</h5>\r\n        </div>\r\n      </div>\r\n    </div>\r\n    <div class=\"col-md-2 mb-1 text-center\">\r\n      <div class=\"card shadow p-3  bg-white rounded\" style=\"width: 18rem; padding:0px\">\r\n        <div class=\"card-body text-center\" style=\"padding:15px;\">\r\n          <h5 class=\"card-title\">Transactions non minées</h5>\r\n          <h5 class=\"card-title\">{{totalTransactionUnMined}}</h5>\r\n        </div>\r\n      </div>\r\n    </div>\r\n  </div>\r\n  <div class=\"row col-lg-12 mb-4 \">\r\n    <div class=\"col-lg-4 \"></div>\r\n    <div class=\"col-sm-2 mb-1 text-center\">\r\n      <div class=\"card shadow p-3 bg-white rounded\" style=\"width: 18rem;\">\r\n        <div class=\"card-body text-center\"  style=\"padding:15px;\">\r\n          <h5 class=\"card-title\">Utilisateurs</h5>\r\n          <h5 class=\"card-title\">{{totalUsers}}</h5>\r\n        </div>\r\n      </div>\r\n    </div>\r\n    <div class=\"col-sm-2 mb-1 text-center\">\r\n      <div class=\"card shadow p-3 bg-white rounded\" style=\"width: 18rem;\">\r\n        <div class=\"card-body text-center\"  style=\"padding:15px;\">\r\n          <h5 class=\"card-title\">Valeur</h5>\r\n          <h5 class=\"card-title\">20 000 Eur</h5>\r\n        </div>\r\n      </div>\r\n    </div>\r\n    <div class=\"col-lg-4 \"></div>\r\n  </div>  \r\n  <div class=\"row col-lg-12\" style=\"background-color:white; width:100%; padding:0px; margin:0px\">\r\n    <div class=\"col-lg-2\" style=\"padding:0px\"></div>\r\n    <div class=\"col-lg-8\">\r\n      <div class=\"row col-lg-12 rounded-top\" style=\"margin-left:10px;min-width:619px; background-color:#00285c; padding:0px; margin:0px;\">\r\n        <div class=\"btn-group btn-group-toggle\" style=\"margin:5px;background-color:#00285c; width:auto\" data-toggle=\"buttons\">\r\n          <label>\r\n            <button class=\"btn btn-info active\" (click)=\"getTransactions()\" autocomplete=\"off\"> Transactions </button>\r\n          </label>\r\n          <label>\r\n            <button class=\"btn btn-info active\" (click)=\"getBlocks()\" autocomplete=\"off\"> Blocs </button>\r\n          </label>\r\n          <label>\r\n            <button class=\"btn btn-info active\" (click)=\"getNodes()\" autocomplete=\"off\"> Noeuds </button>\r\n          </label>\r\n          <label>\r\n            <button class=\"btn btn-info active\" (click)=\"getWallets()\" autocomplete=\"off\"> Wallets </button>\r\n          </label>\r\n        </div>\r\n      </div>\r\n      <table *ngIf=\"showTransaction == true\" id=\"table\" class=\"table table-striped  table-sm col-lg-12 shadow-lg\" style=\"margin-top:0px; border-radius: 5px 5px;\">\r\n        <thead style=\"background-color:#00285c; border-top-left-radius: 40px 40px;\">\r\n          <tr>\r\n            <th class=\"text-center\" scope=\"col\" style=\"border-top: 0px;color:white; padding-bottom:0px; max-width:3em;\">Id</th>\r\n            <th class=\"text-center\" scope=\"col\" style=\"border-top: 0px;color:white; padding-bottom:0px\">Minée</th>\r\n            <th class=\"text-center\" scope=\"col\" style=\"border-top: 0px;color:white; padding-bottom:0px\">Message</th>\r\n            <th class=\"text-center\" scope=\"col\" style=\"border-top: 0px;color:white; padding-bottom:0px\">Id receveur</th>\r\n            <th class=\"text-center\" scope=\"col\" style=\"border-top: 0px;color:white; padding-bottom:0px\">Id lanceur</th>\r\n            <th class=\"text-center\" scope=\"col\" style=\"border-top: 0px;color:white; padding-bottom:0px\">Timestamp</th>\r\n          </tr>\r\n        </thead>\r\n        <tbody>\r\n          <tr *ngFor=\"let t of tableInfo | paginate: { itemsPerPage: 10, currentPage: p }\">\r\n            <th class=\"text-center\" scope=\"row\">{{t.id}}</th>\r\n            <td class=\"text-center\">{{t.mined}}</td>\r\n            <td class=\"text-center\">{{t.message.substring(0,40)}}...</td>\r\n            <td class=\"text-center\">{{t.receiverId}}</td>\r\n            <td class=\"text-center\">{{t.senderId}}</td>\r\n            <td class=\"text-center\">{{t.timestamp}}</td>\r\n          </tr>\r\n        </tbody>\r\n        <tfoot>\r\n          <ul class=\"pagination col-lg-12\">\r\n            <pagination-controls (pageChange)=\"p = $event\"></pagination-controls>\r\n          </ul>\r\n        </tfoot>\r\n      </table>\r\n\r\n      <table *ngIf=\"showBlock == true\" id=\"table\" class=\"table table-striped  table-sm col-lg-12 shadow-lg\" style=\"margin-top:0px; border-radius: 5px 5px;\">\r\n        <thead style=\"background-color:#00285c; border-top-left-radius: 40px 40px;\">\r\n          <tr>\r\n            <th class=\"text-center\" scope=\"col\" style=\"border-top: 0px;color:white; padding-bottom:0px\">Id</th>\r\n            <th class=\"text-center\" scope=\"col\" style=\"border-top: 0px;color:white; padding-bottom:0px\">Hash précédent</th>\r\n            <th class=\"text-center\" scope=\"col\" style=\"border-top: 0px;color:white; padding-bottom:0px\">Hash</th>\r\n            <th class=\"text-center\" scope=\"col\" style=\"border-top: 0px;color:white; padding-bottom:0px\">Nonce</th>\r\n            <th class=\"text-center\" scope=\"col\" style=\"border-top: 0px;color:white; padding-bottom:0px\">total transactions</th>\r\n            <th class=\"text-center\" scope=\"col\" style=\"border-top: 0px;color:white; padding-bottom:0px\">Timestamp</th>\r\n            <th class=\"text-center\" scope=\"col\" style=\"border-top: 0px;color:white; padding-bottom:0px\">Action</th>\r\n          </tr>\r\n        </thead>\r\n        <tbody>\r\n          <tr *ngFor=\"let t of tableInfo | paginate: { itemsPerPage: 10, currentPage: p }\">\r\n            <th class=\"text-center\" scope=\"row\">{{t.id}}</th>\r\n            <td class=\"text-center\">{{t.prevHash.substring(0,20)}}...</td>\r\n            <td class=\"text-center\">{{t.hash.substring(0,20)}}...</td>\r\n            <td class=\"text-center\">{{t.nonce}}</td>\r\n            <td class=\"text-center\">{{t.transactionsCount}}</td>\r\n            <td class=\"text-center\">{{t.timestamp}}</td>\r\n            <td class=\"text-center\"><button class=\"btn btn-info\" (click)=\"blockDetail(t.id)\">Lire</button></td>\r\n          </tr>\r\n        </tbody>\r\n        <tfoot>\r\n          <ul class=\"pagination col-lg-12\">\r\n            <pagination-controls (pageChange)=\"p = $event\"></pagination-controls>\r\n          </ul>\r\n        </tfoot>\r\n        \r\n      </table>\r\n\r\n      <table *ngIf=\"showNode == true\" id=\"table\" class=\"table table-striped  table-sm col-lg-12 shadow-lg\" style=\"margin-top:0px; border-radius: 5px 5px;\">\r\n        <thead style=\"background-color:#00285c; border-top-left-radius: 40px 40px;\">\r\n          <tr>\r\n            <th class=\"text-center\" scope=\"col\" style=\"border-top: 0px;color:white; padding-bottom:0px\">Adresse</th>\r\n            <th class=\"text-center\" scope=\"col\" style=\"border-top: 0px;color:white; padding-bottom:0px\">Port</th>\r\n          </tr>\r\n        </thead>\r\n        <tbody>\r\n          <tr *ngFor=\"let t of tableInfo | paginate: { itemsPerPage: 10, currentPage: p }\">\r\n            <th class=\"text-center\" scope=\"row\">{{t.host}}</th>\r\n            <td class=\"text-center\">{{t.port}}</td>\r\n          </tr>\r\n        </tbody>\r\n        <tfoot>\r\n          <ul class=\"pagination col-lg-12\">\r\n            <pagination-controls (pageChange)=\"p = $event\"></pagination-controls>\r\n          </ul>\r\n        </tfoot>\r\n      </table>\r\n\r\n      <table *ngIf=\"showWallet == true\" id=\"table\" class=\"table table-striped  table-sm col-lg-12 shadow-lg\" style=\"margin-top:0px; border-radius: 5px 5px;\">\r\n        <thead style=\"background-color:#00285c; border-top-left-radius: 40px 40px;\">\r\n          <tr>\r\n            <th class=\"text-center\" scope=\"col\" style=\"border-top: 0px;color:white; padding-bottom:0px\">Id</th>\r\n            <th class=\"text-center\" scope=\"col\" style=\"border-top: 0px;color:white; padding-bottom:0px\">Nom</th>\r\n            <th class=\"text-center\" scope=\"col\" style=\"border-top: 0px;color:white; padding-bottom:0px\">mail</th>\r\n          </tr>\r\n        </thead>\r\n        <tbody>\r\n          <tr *ngFor=\"let w of tableInfo | paginate: { itemsPerPage: 10, currentPage: p }\">\r\n            <th class=\"text-center\">{{w.id}}</th>\r\n            <td class=\"text-center\">{{w.name}}</td>\r\n            <th class=\"text-center\">{{w.mail}}</th>\r\n          </tr>\r\n        </tbody>\r\n        <tfoot>\r\n          <ul class=\"pagination col-lg-12\">\r\n            <pagination-controls (pageChange)=\"p = $event\"></pagination-controls>\r\n          </ul>\r\n        </tfoot>\r\n      </table>\r\n    </div>\r\n    <div class=\"col-lg-2\"></div>\r\n  </div>\r\n\r\n</div>\r\n\r\n\r\n\r\n"

/***/ }),

/***/ "./node_modules/raw-loader/index.js!./src/app/nav-menu/nav-menu.component.html":
/*!****************************************************************************!*\
  !*** ./node_modules/raw-loader!./src/app/nav-menu/nav-menu.component.html ***!
  \****************************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "<header>\r\n  <link rel=\"stylesheet\" href=\"https://use.fontawesome.com/releases/v5.8.2/css/all.css\" integrity=\"sha384-oS3vJWv+0UjzBfQzYUhtDYW+Pj2yciDJxpsK1OYPAYjqT085Qq/1cq5FLXAZQ7Ay\" crossorigin=\"anonymous\">\r\n  <nav class='navbar navbar-expand-sm navbar-toggleable-sm  box-shadow  ' style=\"background-color:#00285c; padding:0px 0px 0px -10px\">\r\n    <div class=\"container\">\r\n      <div class=\"row col-lg-12\">\r\n        <a class=\"navbar-brand text-light\" (click)=\"home()\" style=\"font-weight: 800; font-size:2em; height:40px;margin-right:5px; line-height:15px\">SUPBANK</a>\r\n        <!--<span class=\"fab fa-bitcoin fa-2x text-light\" id=\"basic-addon1\"></span>-->\r\n        <img src=\"../../assets/facebook_profile_image.png\" class=\"rounded \" style=\"width: 2em;height: 2em; \" />\r\n        <button class=\"navbar-toggler\" type=\"button\" data-toggle=\"collapse\" data-target=\".navbar-collapse\" aria-label=\"Toggle navigation\"\r\n                [attr.aria-expanded]=\"isExpanded\" (click)=\"toggle()\">\r\n          <span class=\"navbar-toggler-icon\"></span>\r\n        </button>\r\n        <div class=\"navbar-collapse collapse d-sm-inline-flex flex-sm-row-reverse mb-3\" [ngClass]='{\"show\": isExpanded}'>\r\n          <ul *ngIf=\"isLogged == false\" class=\"navbar-nav flex-grow\">\r\n            <li class=\"nav-item\" [routerLinkActive]='[\"link-active\"]' style=\"margin-right:1em;\" [routerLinkActiveOptions]='{ exact: true }'>\r\n              <button type=\"button\" class=\"btn btn-info\" data-toggle=\"modal\" data-target=\"#LoginModal\">\r\n                Connexion\r\n              </button>\r\n            </li>\r\n            <li *ngIf=\"showSearchBar == true\" class=\"nav-item\" [routerLinkActive]='[\"link-active\"]'>\r\n              <button type=\"button\" class=\"btn  btn-info\" data-toggle=\"modal\" data-target=\"#RegisterModal\">S'inscrire</button>\r\n            </li>\r\n          </ul>\r\n          <ul *ngIf=\"isLogged == true\" class=\"navbar-nav flex-grow\">\r\n            <li class=\"nav-item\" [routerLinkActive]='[\"link-active\"]' style=\"margin-right:1em;\" [routerLinkActiveOptions]='{ exact: true }'>\r\n              <button type=\"button\" class=\"btn btn-info\" [routerLink]=\"['/wallet']\">\r\n                Portefeuille  <span class=\"fas fa-wallet  text-light\" id=\"basic-addon1\"></span>\r\n              </button>\r\n            </li>\r\n            <li class=\"nav-item\">\r\n              <button type=\"button\" class=\"btn  btn-danger\" (click)=\"logOut()\" title=\"Déconnexion\"><span class=\"far fa-times-circle\" style=\"color:white\"></span></button>\r\n            </li>\r\n          </ul>\r\n        </div>\r\n      </div>\r\n    </div>\r\n  </nav>\r\n  <div  class=\"row shadow-lg col mb-5\" style=\"background-color:#00285c; padding:0px 0px 0px 0px; margin-right:0px; margin-left:0px\">\r\n    <div class=\"col-lg-12\">\r\n      <div class=\"row mb-3\">\r\n        <div class=\"text-center col-lg-12\">\r\n          <span class=\"h1 text-light\" style=\"font-weight: 600; font-family: Montserrat, sans-serif;\">Explorateur de blocs</span>\r\n        </div>\r\n      </div>\r\n      <div class=\"row text-center\">\r\n        <div class=\"col-lg-4\"></div>\r\n        <div class=\"input-group mb-3 col-lg-4\">\r\n          <div class=\"input-group-prepend \" style=\" background-color:white; border-radius:2px; padding: 2px 2px 2px 2px\">\r\n            <span class=\"fas fa-search-dollar fa-2x\" id=\"basic-addon1\" (click)=\"search(searchText)\" style=\"color:#1a3c69 \"></span>\r\n          </div>\r\n          <input class=\"form-control\" type=\"text\" name=\"search\" [(ngModel)]=\"searchText\" autocomplete=\"off\">\r\n        </div>\r\n        <div class=\"col-lg-4\"></div>\r\n      </div>\r\n      <div class=\"row text-center\">\r\n        <div class=\" col-lg-12 text-center\">\r\n          <p class=\"text-light\">Vous pouver rechercher des transactions en fonction de l'adresse d'un wallet (Id ou Nom), des noeuds ou de l'identifiant d'un bloc</p>\r\n        </div>\r\n      </div>\r\n    </div>\r\n\r\n  </div>\r\n  <div *ngIf=\"showSearchResult == true\" class=\"row col-lg-12\" style=\"background-color:white; width:100%; padding:0px; margin:0px\">\r\n    <div class=\"col-lg-2\" style=\"padding:0px\"></div>\r\n    <table id=\"table\" class=\"table table-striped  table-sm col-lg-8 shadow-lg\" style=\"margin-top:0px; border-radius: 5px 5px;\">\r\n      <thead style=\"background-color:#00285c; border-top-left-radius: 40px 40px;\">\r\n      <tr>\r\n        <th class=\"text-center\" scope=\"col\" style=\"border-top: 0px;color:white; padding-bottom:0px; max-width:3em;\">Id</th>\r\n        <th class=\"text-center\" scope=\"col\" style=\"border-top: 0px;color:white; padding-bottom:0px\">Minée</th>\r\n        <th class=\"text-center\" scope=\"col\" style=\"border-top: 0px;color:white; padding-bottom:0px\">Message</th>\r\n        <th class=\"text-center\" scope=\"col\" style=\"border-top: 0px;color:white; padding-bottom:0px\">Id receveur</th>\r\n        <th class=\"text-center\" scope=\"col\" style=\"border-top: 0px;color:white; padding-bottom:0px\">Id lanceur</th>\r\n        <th class=\"text-center\" scope=\"col\" style=\"border-top: 0px;color:white; padding-bottom:0px\">Timestamp</th>\r\n      </tr>\r\n      </thead>\r\n      <tbody>\r\n      <tr *ngFor=\"let t of tableInfo | paginate: { itemsPerPage: 10, currentPage: p }\">\r\n        <th class=\"text-center\" scope=\"row\">{{t.id}}</th>\r\n        <td class=\"text-center\">{{t.mined}}</td>\r\n        <td class=\"text-center\">{{t.message.substring(0,40)}}...</td>\r\n        <td class=\"text-center\">{{t.receiverId}}</td>\r\n        <td class=\"text-center\">{{t.senderId}}</td>\r\n        <td class=\"text-center\">{{t.timestamp}}</td>\r\n      </tr>\r\n      </tbody>\r\n      <ul class=\"pagination\">\r\n        <pagination-controls (pageChange)=\"p = $event\"></pagination-controls>\r\n      </ul>\r\n    </table>\r\n    <div class=\"col-lg-2\"></div>\r\n  </div>\r\n  <div class=\"modal fade in show \" id=\"LoginModal\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"exampleModalLabel\">\r\n    <div class=\"modal-dialog \" role=\"document\">\r\n      <div class=\"modal-content\">\r\n        <div class=\"card shadow-lg\">\r\n\r\n          <h5 class=\"card-header white-text text-center py-4\" style=\"background-color:#f0f0f1\">\r\n            <strong>Connexion</strong>\r\n          </h5>\r\n\r\n          <!--Card content-->\r\n          <div class=\"card-body px-lg-5 pt-0\" style=\"margin-top:20px;\">\r\n\r\n            <!-- Form -->\r\n            <form class=\"text-center\" [formGroup]=\"contactForm\" (ngSubmit)=\" logIn()\" style=\"color: #757575;\">\r\n\r\n              Merci de rentrer votre addresse mail si vous l'avez renseignez lors de votre inscription, sinon le nom de votre wallet.\r\n\r\n              <!-- Email -->\r\n              <input type=\"email\" formControlName=\"email\" id=\"materialLoginFormEmail\" placeholder=\"Mail\" class=\"form-control\"  required>\r\n\r\n              <p>ou</p>\r\n              <!-- Password -->\r\n              <input type=\"text\"formControlName=\"name\" id=\"materialLoginFormPassword\" placeholder=\"Nom\" class=\"form-control\">\r\n\r\n              <div class=\"d-flex justify-content-around\">\r\n\r\n              </div>\r\n\r\n              <!-- Sign in button -->\r\n              <button class=\"btn btn-outline-info btn-rounded btn-block my-4 waves-effect z-depth-0\" type=\"submit\">Connexion</button>\r\n\r\n              <!-- Social login -->\r\n              <p>ou connectez-vous avec:</p>\r\n              <a type=\"button\" data-toggle=\"tooltip\" (click)=\"facebookLogin()\" data-placement=\"right\" title=\"Connexion avec Facebook\" class=\"btn-floating btn-fb btn-sm btn-primary text-light\">\r\n                <i class=\"fab fa-facebook-f fa-2x\"></i>\r\n              </a>\r\n              <a type=\"button\" data-toggle=\"tooltip\"  (click)=\"googleLogin()\" data-placement=\"right\" title=\"Connexion avec Google\" class=\"btn-floating btn-git btn-sm btn-primary text-light\">\r\n                <i class=\"fab fa-google fa-2x\"></i>\r\n              </a>\r\n\r\n            </form>\r\n            <!-- Form -->\r\n\r\n          </div>\r\n\r\n        </div>\r\n      </div>\r\n    </div>\r\n\r\n  </div>\r\n\r\n\r\n  <div class=\"modal fade in show \" id=\"RegisterModal\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"exampleModalLabel\">\r\n    <div class=\"modal-dialog \" role=\"document\">\r\n      <div class=\"modal-content\">\r\n        <div class=\"card shadow-lg\">\r\n\r\n          <h5 class=\"card-header white-text text-center py-4\" style=\"background-color:#f0f0f1\">\r\n            <strong>Inscription</strong>\r\n          </h5>\r\n\r\n          <!--Card content-->\r\n          <div class=\"card-body px-lg-5 pt-0\" style=\"margin-top:20px;\">\r\n\r\n            <!-- Form -->\r\n            <form [formGroup]=\"registerForm\" (ngSubmit)=\" register()\" class=\"text-center\" style=\"color: #757575;\">\r\n\r\n              <!-- Email -->\r\n\r\n              <input type=\"email\" formControlName=\"email\" id=\"materialLoginFormEmailRegister\" class=\"form-control\">\r\n              <label for=\"materialLoginFormEmailRegister\" style=\"color:#1a3c69\">E-mail&nbsp;<span class=\"fas fa-at\" id=\"basic-addon1\" style=\"color:#1a3c69\"></span></label>\r\n\r\n              <!-- Password -->\r\n              <input type=\"text\" formControlName=\"name\" id=\"materialLoginFormPasswordRegister\" class=\"form-control\">\r\n              <label for=\"materialLoginFormPasswordRegister\">Nom&nbsp;<span class=\"fas fa-key\" id=\"basic-addon1\" style=\"color:#1a3c69\"></span></label>\r\n\r\n              <div class=\"d-flex justify-content-around\">\r\n\r\n              </div>\r\n\r\n              <!-- Register button -->\r\n              <button class=\"btn btn-outline-info btn-rounded btn-block my-4 waves-effect z-depth-0\" type=\"submit\">Inscription</button>\r\n\r\n\r\n              <!-- Social login -->\r\n              <p>or register with:</p>\r\n              <a type=\"button\" data-toggle=\"tooltip\" (click)=\"facebookRegister()\" data-placement=\"right\" title=\"Inscription avec Facebook\" class=\"btn-floating btn-fb btn-sm btn-primary text-light\">\r\n                <i class=\"fab fa-facebook-f fa-2x\"></i>\r\n              </a>\r\n              <a type=\"button\" data-toggle=\"tooltip\" (click)=\"googleRegister()\" data-placement=\"right\" title=\"Inscription avec Google\" class=\"btn-floating btn-git btn-sm btn-primary text-light\">\r\n                <i class=\"fab fa-google fa-2x\"></i>\r\n              </a>\r\n\r\n            </form>\r\n            <!-- Form -->\r\n\r\n          </div>\r\n\r\n        </div>\r\n      </div>\r\n    </div>\r\n\r\n  </div>\r\n\r\n\r\n\r\n</header>\r\n"

/***/ }),

/***/ "./node_modules/raw-loader/index.js!./src/app/subscribe/subscribe.component.html":
/*!******************************************************************************!*\
  !*** ./node_modules/raw-loader!./src/app/subscribe/subscribe.component.html ***!
  \******************************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "<div class=\"row\">\r\n  <div class=\"col-lg-4\"></div>\r\n  <div class=\"col-lg-4 \" style=\"margin-right:0px; margin-top:2em\">\r\n\r\n    <!-- Material form contact -->\r\n    <div class=\"card shadow-lg\">\r\n\r\n      <h5 class=\"card-header info-color white-text text-center py-4\">\r\n        <strong>Inscription</strong>\r\n      </h5>\r\n\r\n      <!--Card content-->\r\n      <div class=\"col-md-2\"></div>\r\n      <div class=\"card-body px-lg-5 pt-0\">\r\n\r\n        <!-- Form -->\r\n        <form class=\"text-center\" style=\"color: #757575;\">\r\n\r\n          <!-- Name -->\r\n          <div class=\"md-form mt-3\">\r\n            <input type=\"text\" id=\"materialContactFormName\" class=\"form-control\">\r\n            <label for=\"materialContactFormLastName\">Name</label>\r\n          </div>\r\n          <div class=\"md-form mt-3\">\r\n            <input type=\"text\" id=\"materialContactFormName\" class=\"form-control\">\r\n            <label for=\"materialContactFormFirstName\">Prénom</label>\r\n          </div>\r\n          <!-- E-mail -->\r\n          <div class=\"md-form\">\r\n            <input type=\"email\" id=\"materialContactFormEmail\" class=\"form-control\">\r\n            <label for=\"materialContactFormEmail\">E-mail</label>\r\n          </div>\r\n          <div class=\"md-form mt-3\">\r\n            <input type=\"text\" id=\"materialContactFormName\" class=\"form-control\">\r\n            <label for=\"materialContactFormPwd\">Mot de passe</label>\r\n          </div>\r\n          <div class=\"md-form mt-3\">\r\n            <input type=\"text\" id=\"materialContactFormName\" class=\"form-control\">\r\n            <label for=\"materialContactFormConfirmPwd\">Confirmation du mot de passe</label>\r\n          </div>\r\n          <!--Message-->\r\n          <!-- Send button -->\r\n          <button class=\"btn btn-outline-info btn-rounded btn-block z-depth-0 my-4 waves-effect\" type=\"submit\">Send</button>\r\n\r\n        </form>\r\n\r\n        <!-- Form -->\r\n\r\n      </div>\r\n      <div class=\"col-md-2\"></div>\r\n    </div>\r\n    <!-- Material form contact -->\r\n  </div>\r\n  <div class=\"col-lg-4\"></div>\r\n\r\n</div>\r\n"

/***/ }),

/***/ "./node_modules/raw-loader/index.js!./src/app/trasaction-modal/trasaction-modal.component.html":
/*!********************************************************************************************!*\
  !*** ./node_modules/raw-loader!./src/app/trasaction-modal/trasaction-modal.component.html ***!
  \********************************************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "<p>\n  trasaction-modal works!\n</p>\n"

/***/ }),

/***/ "./node_modules/raw-loader/index.js!./src/app/wallet/wallet.component.html":
/*!************************************************************************!*\
  !*** ./node_modules/raw-loader!./src/app/wallet/wallet.component.html ***!
  \************************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "<div class=\"container\">\n\n    <!--Wallet information  part-->\n    <div class=\"card\">\n        <div class=\"card-header\">\n            <h2>Votre wallet</h2>\n        </div>\n        <div class=\"card-body\">\n            <h4>Nom : {{ wallet.name }}</h4>\n            <h4>ID : {{ wallet.id }}</h4>\n            <h4>Supcoins : {{ wallet.amount }}</h4>\n            <p *ngIf=\"wallet.mail != null\" class=\"lead\">Mail : {{ wallet.mail }}</p>\n        </div>\n    </div><br>\n\n    <!--Wallet create transaction part-->\n    <div class=\"card\">\n        <div class=\"card-header\">\n            <h2>Création d'une transaction</h2>\n        </div>\n        <div class=\"card-body\">\r\n          <form>\r\n            <div class=\"form-group\">\r\n              <label for=\"inputReceiverId\">ID du wallet de destination</label>\r\n              <input type=\"number\" class=\"form-control\" id=\"inputReceiverId\" aria-describedby=\"receiverIdHelp\" placeholder=\"ID du destinataire\" [(ngModel)]=\"trReceiverId\" [ngModelOptions]=\"{standalone: true}\" required>\r\n              <small id=\"receiverIdHelp\" class=\"form-text text-muted\">L'ID du wallet cible de cette transaction.</small>\r\n            </div>\r\n            <div class=\"form-group\">\r\n              <label for=\"inputAmount\">Montant de la transaction (en SupCoin)</label>\r\n              <input type=\"number\" class=\"form-control\" id=\"inputAmount\" placeholder=\"Montant\" [(ngModel)]=\"trAmount\" [ngModelOptions]=\"{standalone: true}\" required>\r\n            </div>\r\n            <div class=\"form-group\">\r\n              <label for=\"inputMessage\">Message a envoyer avec la transaction</label>\r\n              <input type=\"text\" class=\"form-control\" id=\"inputMessage\" aria-describedby=\"messageHelp\" placeholder=\"Message accompagnant la transaction\" [(ngModel)]=\"trMessage\" [ngModelOptions]=\"{standalone: true}\" required>\r\n              <small id=\"messageHelp\" class=\"form-text text-muted\">Votre message sera crypté et lisible uniquement par le destinataire.</small>\r\n            </div>\r\n            <button class=\"btn btn-info\" (click)=\"createTransaction()\">Envoyer</button>\r\n          </form>\r\n          <br/>\r\n          <div class=\"alert alert-danger\" *ngIf=\"showAlert == true\" role=\"alert\">\r\n            Une erruer est survenue, consultez la console de la blockchain pour plus d'information.\r\n          </div>\r\n        </div>\n    </div><br>\n\n    <!--Wallet transactions done-->\n          <div>\r\n            <h1>Transactions reçues</h1>\r\n            <table id=\"receivedTable\" class=\"table table-striped  table-sm col-lg-12 shadow-lg\"\r\n                   style=\"margin-top:1em; border-radius: 5px 5px;\">\r\n              <thead style=\"background-color:#00285c; border-top-left-radius: 40px 40px;\">\r\n                <tr>\r\n                  <th class=\"text-center\" scope=\"col\" style=\"border-top: 0px;color:white; padding-bottom:0px\">ID</th>\r\n                  <th class=\"text-center\" scope=\"col\" style=\"border-top: 0px;color:white; padding-bottom:0px\">Message</th>\r\n                  <th class=\"text-center\" scope=\"col\" style=\"border-top: 0px;color:white; padding-bottom:0px\">Montant</th>\r\n                  <th class=\"text-center\" scope=\"col\" style=\"border-top: 0px;color:white; padding-bottom:0px\">Id lanceur</th>\r\n                  <th class=\"text-center\" scope=\"col\" style=\"border-top: 0px;color:white; padding-bottom:0px\">Minée</th>\r\n                  <th class=\"text-center\" scope=\"col\" style=\"border-top: 0px;color:white; padding-bottom:0px\">Timestamp</th>\r\n                  <th class=\"text-center\" scope=\"col\" style=\"border-top: 0px;color:white; padding-bottom:0px\">Action</th>\r\n                </tr>\r\n              </thead>\r\n              <tbody>\r\n                <tr *ngFor=\"let t of receivedTransactions | paginate: { itemsPerPage: 10, currentPage: p }\">\r\n                  <th class=\"text-center\" scope=\"row\">{{t.id}}</th>\r\n                  <td class=\"text-center\">{{t.message.substring(0, 20)}}...</td>\r\n                  <th class=\"text-center\" scope=\"row\">{{t.amount}}</th>\r\n                  <td class=\"text-center\">{{t.senderId}}</td>\r\n                  <td class=\"text-center\">{{t.mined}}</td>\r\n                  <td class=\"text-center\">{{t.timestamp}}</td>\r\n                  <td class=\"text-center\"><button class=\"btn btn-info\" (click)=\"decryptTransaction(t.id,t.amount, t.senderId, t.mined, t.timestamp)\" data-toggle=\"modal\" data-target=\"#transactionModal\">Lire</button></td>\r\n                </tr>\r\n              </tbody>\r\n              <ul class=\"pagination\">\r\n                <pagination-controls (pageChange)=\"p = $event\"></pagination-controls>\r\n              </ul>\r\n            </table>\r\n            <h1>Transactions envoyées</h1>\r\n            <table id=\"sendedTable\" class=\"table table-striped  table-sm col-lg-12 shadow-lg\"\r\n                   style=\"margin-top:1em; border-radius: 5px 5px;\">\r\n              <thead style=\"background-color:#00285c; border-top-left-radius: 40px 40px;\">\r\n                <tr>\r\n                  <th class=\"text-center\" scope=\"col\" style=\"border-top: 0px;color:white; padding-bottom:0px\">ID</th>\r\n                  <th class=\"text-center\" scope=\"col\" style=\"border-top: 0px;color:white; padding-bottom:0px\">Message</th>\r\n                  <th class=\"text-center\" scope=\"col\" style=\"border-top: 0px;color:white; padding-bottom:0px\">Montant</th>\r\n                  <th class=\"text-center\" scope=\"col\" style=\"border-top: 0px;color:white; padding-bottom:0px\">Id receveur</th>\r\n                  <th class=\"text-center\" scope=\"col\" style=\"border-top: 0px;color:white; padding-bottom:0px\">Minée</th>\r\n                  <th class=\"text-center\" scope=\"col\" style=\"border-top: 0px;color:white; padding-bottom:0px\">Timestamp</th>\r\n                </tr>\r\n              </thead>\r\n              <tbody>\r\n                <tr *ngFor=\"let t of sendedTransactions | paginate: { itemsPerPage: 10, currentPage: p }\">\r\n                  <th class=\"text-center\" scope=\"row\">{{t.id}}</th>\r\n                  <td class=\"text-center\">{{t.message.substring(0, 20)}}...</td>\r\n                  <th class=\"text-center\" scope=\"row\">{{t.amount}}</th>\r\n                  <td class=\"text-center\">{{t.receiverId}}</td>\r\n                  <td class=\"text-center\">{{t.mined}}</td>\r\n                  <td class=\"text-center\">{{t.timestamp}}</td>\r\n                </tr>\r\n              </tbody>\r\n              <ul class=\"pagination\">\r\n                <pagination-controls (pageChange)=\"p = $event\"></pagination-controls>\r\n              </ul>\r\n            </table>           \r\n          </div>\n</div>\n\n<div class=\"modal fade\" id=\"transactionModal\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"exampleModalLabel\" aria-hidden=\"true\">\r\n  <div class=\"modal-dialog\" role=\"document\">\r\n    <div class=\"modal-content\">\r\n      <div class=\"modal-header\">\r\n        <h5 class=\"modal-title\" id=\"exampleModalLabel\">Modal title</h5>\r\n        <button type=\"button\" class=\"close\" data-dismiss=\"modal\" aria-label=\"Close\">\r\n          <span aria-hidden=\"true\">&times;</span>\r\n        </button>\r\n      </div>\r\n      <div class=\"modal-body\">\r\n        ...\r\n      </div>\r\n      <div class=\"modal-footer\">\r\n        <button type=\"button\" class=\"btn btn-secondary\" data-dismiss=\"modal\">Close</button>\r\n        <button type=\"button\" class=\"btn btn-primary\">Save changes</button>\r\n      </div>\r\n    </div>\r\n  </div>\r\n</div>\n\n\n\n"

/***/ }),

/***/ "./src/$$_lazy_route_resource lazy recursive":
/*!**********************************************************!*\
  !*** ./src/$$_lazy_route_resource lazy namespace object ***!
  \**********************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

function webpackEmptyAsyncContext(req) {
	// Here Promise.resolve().then() is used instead of new Promise() to prevent
	// uncaught exception popping up in devtools
	return Promise.resolve().then(function() {
		var e = new Error("Cannot find module '" + req + "'");
		e.code = 'MODULE_NOT_FOUND';
		throw e;
	});
}
webpackEmptyAsyncContext.keys = function() { return []; };
webpackEmptyAsyncContext.resolve = webpackEmptyAsyncContext;
module.exports = webpackEmptyAsyncContext;
webpackEmptyAsyncContext.id = "./src/$$_lazy_route_resource lazy recursive";

/***/ }),

/***/ "./src/app/Model/User.ts":
/*!*******************************!*\
  !*** ./src/app/Model/User.ts ***!
  \*******************************/
/*! exports provided: User */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "User", function() { return User; });
var User = /** @class */ (function () {
    function User() {
    }
    return User;
}());



/***/ }),

/***/ "./src/app/Model/transactionViewModel.ts":
/*!***********************************************!*\
  !*** ./src/app/Model/transactionViewModel.ts ***!
  \***********************************************/
/*! exports provided: transactionViewModel */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "transactionViewModel", function() { return transactionViewModel; });
var transactionViewModel = /** @class */ (function () {
    function transactionViewModel() {
    }
    return transactionViewModel;
}());



/***/ }),

/***/ "./src/app/app.component.css":
/*!***********************************!*\
  !*** ./src/app/app.component.css ***!
  \***********************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "@media (max-width: 767px) {\r\n  /* On small screens, the nav menu spans the full width of the screen. Leave a space for it. */\r\n  .body-content {\r\n    padding-top: 50px;\r\n  }\r\n}\r\n\r\n/*# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbInNyYy9hcHAvYXBwLmNvbXBvbmVudC5jc3MiXSwibmFtZXMiOltdLCJtYXBwaW5ncyI6IkFBQUE7RUFDRSw2RkFBNkY7RUFDN0Y7SUFDRSxpQkFBaUI7RUFDbkI7QUFDRiIsImZpbGUiOiJzcmMvYXBwL2FwcC5jb21wb25lbnQuY3NzIiwic291cmNlc0NvbnRlbnQiOlsiQG1lZGlhIChtYXgtd2lkdGg6IDc2N3B4KSB7XHJcbiAgLyogT24gc21hbGwgc2NyZWVucywgdGhlIG5hdiBtZW51IHNwYW5zIHRoZSBmdWxsIHdpZHRoIG9mIHRoZSBzY3JlZW4uIExlYXZlIGEgc3BhY2UgZm9yIGl0LiAqL1xyXG4gIC5ib2R5LWNvbnRlbnQge1xyXG4gICAgcGFkZGluZy10b3A6IDUwcHg7XHJcbiAgfVxyXG59XHJcbiJdfQ== */"

/***/ }),

/***/ "./src/app/app.component.ts":
/*!**********************************!*\
  !*** ./src/app/app.component.ts ***!
  \**********************************/
/*! exports provided: AppComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "AppComponent", function() { return AppComponent; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};

var AppComponent = /** @class */ (function () {
    function AppComponent() {
        this.showSearchBar = true;
        this.title = 'app';
    }
    AppComponent = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Component"])({
            selector: 'app-root',
            template: __webpack_require__(/*! raw-loader!./app.component.html */ "./node_modules/raw-loader/index.js!./src/app/app.component.html"),
            styles: [__webpack_require__(/*! ./app.component.css */ "./src/app/app.component.css")]
        })
    ], AppComponent);
    return AppComponent;
}());



/***/ }),

/***/ "./src/app/app.module.ts":
/*!*******************************!*\
  !*** ./src/app/app.module.ts ***!
  \*******************************/
/*! exports provided: AppModule */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "AppModule", function() { return AppModule; });
/* harmony import */ var _angular_platform_browser__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/platform-browser */ "./node_modules/@angular/platform-browser/fesm5/platform-browser.js");
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _angular_forms__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! @angular/forms */ "./node_modules/@angular/forms/fesm5/forms.js");
/* harmony import */ var _angular_common_http__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! @angular/common/http */ "./node_modules/@angular/common/fesm5/http.js");
/* harmony import */ var _angular_router__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! @angular/router */ "./node_modules/@angular/router/fesm5/router.js");
/* harmony import */ var _app_component__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! ./app.component */ "./src/app/app.component.ts");
/* harmony import */ var _nav_menu_nav_menu_component__WEBPACK_IMPORTED_MODULE_6__ = __webpack_require__(/*! ./nav-menu/nav-menu.component */ "./src/app/nav-menu/nav-menu.component.ts");
/* harmony import */ var _home_home_component__WEBPACK_IMPORTED_MODULE_7__ = __webpack_require__(/*! ./home/home.component */ "./src/app/home/home.component.ts");
/* harmony import */ var _fetch_data_fetch_data_component__WEBPACK_IMPORTED_MODULE_8__ = __webpack_require__(/*! ./fetch-data/fetch-data.component */ "./src/app/fetch-data/fetch-data.component.ts");
/* harmony import */ var _subscribe_subscribe_component__WEBPACK_IMPORTED_MODULE_9__ = __webpack_require__(/*! ./subscribe/subscribe.component */ "./src/app/subscribe/subscribe.component.ts");
/* harmony import */ var angular5_social_login__WEBPACK_IMPORTED_MODULE_10__ = __webpack_require__(/*! angular5-social-login */ "./node_modules/angular5-social-login/angular5-social-login.umd.js");
/* harmony import */ var angular5_social_login__WEBPACK_IMPORTED_MODULE_10___default = /*#__PURE__*/__webpack_require__.n(angular5_social_login__WEBPACK_IMPORTED_MODULE_10__);
/* harmony import */ var _socialloginConfig__WEBPACK_IMPORTED_MODULE_11__ = __webpack_require__(/*! ./socialloginConfig */ "./src/app/socialloginConfig.ts");
/* harmony import */ var _services_WalletService__WEBPACK_IMPORTED_MODULE_12__ = __webpack_require__(/*! ./services/WalletService */ "./src/app/services/WalletService.ts");
/* harmony import */ var _services_NodeService__WEBPACK_IMPORTED_MODULE_13__ = __webpack_require__(/*! ./services/NodeService */ "./src/app/services/NodeService.ts");
/* harmony import */ var _services_TransactionService__WEBPACK_IMPORTED_MODULE_14__ = __webpack_require__(/*! ./services/TransactionService */ "./src/app/services/TransactionService.ts");
/* harmony import */ var _services_BlockService__WEBPACK_IMPORTED_MODULE_15__ = __webpack_require__(/*! ./services/BlockService */ "./src/app/services/BlockService.ts");
/* harmony import */ var ng2_search_filter__WEBPACK_IMPORTED_MODULE_16__ = __webpack_require__(/*! ng2-search-filter */ "./node_modules/ng2-search-filter/ng2-search-filter.es5.js");
/* harmony import */ var ngx_pagination__WEBPACK_IMPORTED_MODULE_17__ = __webpack_require__(/*! ngx-pagination */ "./node_modules/ngx-pagination/dist/ngx-pagination.js");
/* harmony import */ var _wallet_wallet_component__WEBPACK_IMPORTED_MODULE_18__ = __webpack_require__(/*! ./wallet/wallet.component */ "./src/app/wallet/wallet.component.ts");
/* harmony import */ var _ng_bootstrap_ng_bootstrap__WEBPACK_IMPORTED_MODULE_19__ = __webpack_require__(/*! @ng-bootstrap/ng-bootstrap */ "./node_modules/@ng-bootstrap/ng-bootstrap/fesm5/ng-bootstrap.js");
/* harmony import */ var _trasaction_modal_trasaction_modal_component__WEBPACK_IMPORTED_MODULE_20__ = __webpack_require__(/*! ./trasaction-modal/trasaction-modal.component */ "./src/app/trasaction-modal/trasaction-modal.component.ts");
/* harmony import */ var _block_modal_block_modal_component__WEBPACK_IMPORTED_MODULE_21__ = __webpack_require__(/*! ./block-modal/block-modal.component */ "./src/app/block-modal/block-modal.component.ts");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (undefined && undefined.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};























var AppModule = /** @class */ (function () {
    function AppModule() {
        localStorage.setItem('baseUrl', "");
    }
    AppModule = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["NgModule"])({
            declarations: [
                _app_component__WEBPACK_IMPORTED_MODULE_5__["AppComponent"],
                _nav_menu_nav_menu_component__WEBPACK_IMPORTED_MODULE_6__["NavMenuComponent"],
                _home_home_component__WEBPACK_IMPORTED_MODULE_7__["HomeComponent"],
                _fetch_data_fetch_data_component__WEBPACK_IMPORTED_MODULE_8__["FetchDataComponent"],
                _subscribe_subscribe_component__WEBPACK_IMPORTED_MODULE_9__["SubscribeComponent"],
                _wallet_wallet_component__WEBPACK_IMPORTED_MODULE_18__["WalletComponent"],
                _trasaction_modal_trasaction_modal_component__WEBPACK_IMPORTED_MODULE_20__["TrasactionModalComponent"],
                _block_modal_block_modal_component__WEBPACK_IMPORTED_MODULE_21__["BlockModalComponent"]
            ],
            entryComponents: [_trasaction_modal_trasaction_modal_component__WEBPACK_IMPORTED_MODULE_20__["TrasactionModalComponent"], _block_modal_block_modal_component__WEBPACK_IMPORTED_MODULE_21__["BlockModalComponent"]],
            imports: [
                _ng_bootstrap_ng_bootstrap__WEBPACK_IMPORTED_MODULE_19__["NgbModule"].forRoot(),
                angular5_social_login__WEBPACK_IMPORTED_MODULE_10__["SocialLoginModule"],
                _angular_platform_browser__WEBPACK_IMPORTED_MODULE_0__["BrowserModule"].withServerTransition({ appId: 'ng-cli-universal' }),
                ng2_search_filter__WEBPACK_IMPORTED_MODULE_16__["Ng2SearchPipeModule"],
                _angular_common_http__WEBPACK_IMPORTED_MODULE_3__["HttpClientModule"],
                _angular_forms__WEBPACK_IMPORTED_MODULE_2__["ReactiveFormsModule"],
                ngx_pagination__WEBPACK_IMPORTED_MODULE_17__["NgxPaginationModule"], ng2_search_filter__WEBPACK_IMPORTED_MODULE_16__["Ng2SearchPipeModule"],
                _angular_forms__WEBPACK_IMPORTED_MODULE_2__["FormsModule"],
                _angular_router__WEBPACK_IMPORTED_MODULE_4__["RouterModule"].forRoot([
                    { path: '', component: _home_home_component__WEBPACK_IMPORTED_MODULE_7__["HomeComponent"], pathMatch: 'full' },
                    { path: 'fetch-data', component: _fetch_data_fetch_data_component__WEBPACK_IMPORTED_MODULE_8__["FetchDataComponent"] },
                    { path: 'subscribe', component: _subscribe_subscribe_component__WEBPACK_IMPORTED_MODULE_9__["SubscribeComponent"] },
                    { path: 'wallet', component: _wallet_wallet_component__WEBPACK_IMPORTED_MODULE_18__["WalletComponent"] }
                ])
            ],
            exports: [_trasaction_modal_trasaction_modal_component__WEBPACK_IMPORTED_MODULE_20__["TrasactionModalComponent"], _block_modal_block_modal_component__WEBPACK_IMPORTED_MODULE_21__["BlockModalComponent"]],
            providers: [
                _ng_bootstrap_ng_bootstrap__WEBPACK_IMPORTED_MODULE_19__["NgbActiveModal"],
                _services_WalletService__WEBPACK_IMPORTED_MODULE_12__["WalletService"],
                _services_NodeService__WEBPACK_IMPORTED_MODULE_13__["NodeService"],
                _services_TransactionService__WEBPACK_IMPORTED_MODULE_14__["TransactionService"],
                _services_BlockService__WEBPACK_IMPORTED_MODULE_15__["BlockService"],
                {
                    provide: angular5_social_login__WEBPACK_IMPORTED_MODULE_10__["AuthServiceConfig"], useFactory: _socialloginConfig__WEBPACK_IMPORTED_MODULE_11__["getAuthServiceConfigs"],
                },
            ],
            bootstrap: [_app_component__WEBPACK_IMPORTED_MODULE_5__["AppComponent"]]
        }),
        __metadata("design:paramtypes", [])
    ], AppModule);
    return AppModule;
}());



/***/ }),

/***/ "./src/app/block-modal/block-modal.component.css":
/*!*******************************************************!*\
  !*** ./src/app/block-modal/block-modal.component.css ***!
  \*******************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "\n/*# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbXSwibmFtZXMiOltdLCJtYXBwaW5ncyI6IiIsImZpbGUiOiJzcmMvYXBwL2Jsb2NrLW1vZGFsL2Jsb2NrLW1vZGFsLmNvbXBvbmVudC5jc3MifQ== */"

/***/ }),

/***/ "./src/app/block-modal/block-modal.component.ts":
/*!******************************************************!*\
  !*** ./src/app/block-modal/block-modal.component.ts ***!
  \******************************************************/
/*! exports provided: BlockModalComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "BlockModalComponent", function() { return BlockModalComponent; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (undefined && undefined.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};

var BlockModalComponent = /** @class */ (function () {
    function BlockModalComponent( /*id: number, message: string, amount: number, sender: number, mined: boolean, timestamp: string*/) {
        this.id = localStorage.getItem('id');
        this.hash = localStorage.getItem('hash');
        this.prevHash = localStorage.getItem('prevHash');
        this.nonce = localStorage.getItem('nonce');
        this.transactions = JSON.parse(localStorage.getItem('transactions'));
        this.timestamp = localStorage.getItem('timestamp');
        //this.id = id;
        //this.message = message;
        //this.amount = amount;
        //this.sender = sender;
        //this.mined = mined;
        //this.timestamp = timestamp;
    }
    BlockModalComponent.prototype.ngOnInit = function () {
    };
    BlockModalComponent = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Component"])({
            selector: 'app-block-modal',
            template: __webpack_require__(/*! raw-loader!./block-modal.component.html */ "./node_modules/raw-loader/index.js!./src/app/block-modal/block-modal.component.html"),
            template: "\n    <div class=\"modal-header\">\n      <h2 class=\"modal-title\">D\u00E9tail du bloc.</h2>\n    \n    </div>\n    <div class=\"modal-body\">\n      <div>\n        <h5>Identifiant: {{id}}</h5>\n      <h5>Hash: {{hash}}</h5>\n      <h5>Hash pr\u00E9c\u00E9dent: {{prevHash}}</h5>\n      <h5>Nonce: {{nonce}}</h5>\n      <h5>Timestamp: {{timestamp}}</h5>\n      <h5>Transactions:</h5>\n\n      <table id=\"table\" class=\"table table-striped table-responsive table-sm col-lg-12\" style=\"margin-top:0px; border-radius: 5px 5px;\">\n        <thead style=\"background-color:#00285c; border-top-left-radius: 40px 40px;\">\n          <tr>\n            <th class=\"text-center\" scope=\"col\" style=\"border-top: 0px;color:white; padding-bottom:0px; max-width:3em;\">Id</th>\n            <th class=\"text-center\" scope=\"col\" style=\"border-top: 0px;color:white; padding-bottom:0px\">Min\u00E9e</th>\n            <th class=\"text-center\" scope=\"col\" style=\"border-top: 0px;color:white; padding-bottom:0px\">Message</th>\n            <th class=\"text-center\" scope=\"col\" style=\"border-top: 0px;color:white; padding-bottom:0px\">Id receveur</th>\n            <th class=\"text-center\" scope=\"col\" style=\"border-top: 0px;color:white; padding-bottom:0px\">Id lanceur</th>\n            <th class=\"text-center\" scope=\"col\" style=\"border-top: 0px;color:white; padding-bottom:0px\">Timestamp</th>\n          </tr>\n        </thead>\n        <tbody>\n          <tr *ngFor=\"let t of transactions | paginate: { itemsPerPage: 10, currentPage: p }\">\n            <th class=\"text-center\" scope=\"row\">{{t.id}}</th>\n            <td class=\"text-center\">{{t.mined}}</td>\n            <td class=\"text-center\">{{t.message.substring(0,40)}}...</td>\n            <td class=\"text-center\">{{t.receiverId}}</td>\n            <td class=\"text-center\">{{t.senderId}}</td>\n            <td class=\"text-center\">{{t.timestamp}}</td>\n          </tr>\n        </tbody>\n        <tfoot>\n          <ul class=\"pagination col-lg-12\">\n            <pagination-controls (pageChange)=\"p = $event\"></pagination-controls>\n          </ul>\n        </tfoot>\n      </table>\n\n      </div>      \n    </div>\n  ",
            styles: [__webpack_require__(/*! ./block-modal.component.css */ "./src/app/block-modal/block-modal.component.css")]
        }),
        __metadata("design:paramtypes", [])
    ], BlockModalComponent);
    return BlockModalComponent;
}());



/***/ }),

/***/ "./src/app/fetch-data/fetch-data.component.ts":
/*!****************************************************!*\
  !*** ./src/app/fetch-data/fetch-data.component.ts ***!
  \****************************************************/
/*! exports provided: FetchDataComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "FetchDataComponent", function() { return FetchDataComponent; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _angular_common_http__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/common/http */ "./node_modules/@angular/common/fesm5/http.js");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (undefined && undefined.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};
var __param = (undefined && undefined.__param) || function (paramIndex, decorator) {
    return function (target, key) { decorator(target, key, paramIndex); }
};


var FetchDataComponent = /** @class */ (function () {
    function FetchDataComponent(http, baseUrl) {
        var _this = this;
        http.get(baseUrl + 'api/SampleData/WeatherForecasts').subscribe(function (result) {
            _this.forecasts = result;
        }, function (error) { return console.error(error); });
    }
    FetchDataComponent = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Component"])({
            selector: 'app-fetch-data',
            template: __webpack_require__(/*! raw-loader!./fetch-data.component.html */ "./node_modules/raw-loader/index.js!./src/app/fetch-data/fetch-data.component.html")
        }),
        __param(1, Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Inject"])('BASE_URL')),
        __metadata("design:paramtypes", [_angular_common_http__WEBPACK_IMPORTED_MODULE_1__["HttpClient"], String])
    ], FetchDataComponent);
    return FetchDataComponent;
}());



/***/ }),

/***/ "./src/app/home/home.component.ts":
/*!****************************************!*\
  !*** ./src/app/home/home.component.ts ***!
  \****************************************/
/*! exports provided: HomeComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "HomeComponent", function() { return HomeComponent; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _services_TransactionService__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ../services/TransactionService */ "./src/app/services/TransactionService.ts");
/* harmony import */ var _services_BlockService__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ../services/BlockService */ "./src/app/services/BlockService.ts");
/* harmony import */ var _services_NodeService__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! ../services/NodeService */ "./src/app/services/NodeService.ts");
/* harmony import */ var _services_WalletService__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! ../services/WalletService */ "./src/app/services/WalletService.ts");
/* harmony import */ var _ng_bootstrap_ng_bootstrap__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! @ng-bootstrap/ng-bootstrap */ "./node_modules/@ng-bootstrap/ng-bootstrap/fesm5/ng-bootstrap.js");
/* harmony import */ var _block_modal_block_modal_component__WEBPACK_IMPORTED_MODULE_6__ = __webpack_require__(/*! ../block-modal/block-modal.component */ "./src/app/block-modal/block-modal.component.ts");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (undefined && undefined.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};







var HomeComponent = /** @class */ (function () {
    function HomeComponent(transactionService, blockService, nodeService, walletService, activeModal, modalService) {
        this.transactionService = transactionService;
        this.blockService = blockService;
        this.nodeService = nodeService;
        this.walletService = walletService;
        this.activeModal = activeModal;
        this.modalService = modalService;
        this.tableInfo = [];
        this.showTransaction = true;
        this.showBlock = false;
        this.showNode = false;
        this.showWallet = false;
    }
    HomeComponent.prototype.ngOnInit = function () {
        var _this = this;
        this.totalBlocks = 0;
        this.totalTransactionMined = 0;
        this.totalTransaction = 0;
        this.totalTransactionUnMined = 0;
        this.totalUsers = 0;
        this.transactionService.getTransactions().subscribe(function (res) {
            res.forEach(function (t) {
                _this.totalTransaction += 1;
                if (t.mined == true) {
                    _this.totalTransactionMined += 1;
                }
                else {
                    _this.totalTransactionUnMined += 1;
                }
                _this.tableInfo = res;
            });
        });
        this.blockService.getBlock().subscribe(function (res) {
            res.forEach(function (b) { _this.totalBlocks += 1; });
        });
        this.walletService.walletCount().subscribe(function (res) {
            res.forEach(function (w) { _this.totalUsers += 1; });
        });
    };
    HomeComponent.prototype.getTransactions = function () {
        var _this = this;
        this.transactionService.getTransactions().subscribe(function (res) {
            console.log(res);
            _this.tableInfo = res;
            _this.showTransaction = true;
            _this.showBlock = false;
            _this.showNode = false;
            _this.showWallet = false;
        });
    };
    HomeComponent.prototype.getBlocks = function () {
        var _this = this;
        this.showTransaction = false;
        this.showBlock = true;
        this.showNode = false;
        this.showWallet = false;
        this.blockService.getBlock().subscribe(function (res) {
            console.log(res);
            _this.tableInfo = res;
        });
    };
    HomeComponent.prototype.getNodes = function () {
        var _this = this;
        this.nodeService.getNode().subscribe(function (res) {
            console.log(res);
            _this.tableInfo = res;
            _this.showTransaction = false;
            _this.showBlock = false;
            _this.showNode = true;
            _this.showWallet = false;
        });
    };
    HomeComponent.prototype.getWallets = function () {
        var _this = this;
        this.walletService.walletCount().subscribe(function (res) {
            console.log(res);
            _this.tableInfo = res;
            _this.showTransaction = false;
            _this.showBlock = false;
            _this.showNode = false;
            _this.showWallet = true;
        });
    };
    HomeComponent.prototype.blockDetail = function (id) {
        var _this = this;
        this.blockService.getBlockDetail(id).subscribe(function (block) {
            localStorage.setItem('id', block.id.toString());
            localStorage.setItem('hash', block.hash);
            localStorage.setItem('prevHash', block.prevHash);
            localStorage.setItem('nonce', block.nonce.toString());
            localStorage.setItem('timestamp', block.timestamp);
            localStorage.setItem('transactions', JSON.stringify(block.transactions));
            var modalRef = _this.modalService.open(_block_modal_block_modal_component__WEBPACK_IMPORTED_MODULE_6__["BlockModalComponent"], { size: 'lg' });
            modalRef.componentInstance.name = 'transaction';
        });
    };
    HomeComponent = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Component"])({
            selector: 'app-home',
            template: __webpack_require__(/*! raw-loader!./home.component.html */ "./node_modules/raw-loader/index.js!./src/app/home/home.component.html")
        }),
        __metadata("design:paramtypes", [_services_TransactionService__WEBPACK_IMPORTED_MODULE_1__["TransactionService"], _services_BlockService__WEBPACK_IMPORTED_MODULE_2__["BlockService"],
            _services_NodeService__WEBPACK_IMPORTED_MODULE_3__["NodeService"], _services_WalletService__WEBPACK_IMPORTED_MODULE_4__["WalletService"],
            _ng_bootstrap_ng_bootstrap__WEBPACK_IMPORTED_MODULE_5__["NgbActiveModal"], _ng_bootstrap_ng_bootstrap__WEBPACK_IMPORTED_MODULE_5__["NgbModal"]])
    ], HomeComponent);
    return HomeComponent;
}());



/***/ }),

/***/ "./src/app/nav-menu/nav-menu.component.css":
/*!*************************************************!*\
  !*** ./src/app/nav-menu/nav-menu.component.css ***!
  \*************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "a.navbar-brand {\r\n  white-space: normal;\r\n  text-align: center;\r\n  word-break: break-all;\r\n}\r\n\r\nhtml {\r\n  font-size: 14px;\r\n}\r\n\r\n@media (min-width: 768px) {\r\n  html {\r\n    font-size: 16px;\r\n  }\r\n}\r\n\r\n.box-shadow {\r\n  box-shadow: 0 .25rem .75rem rgba(0, 0, 0, .05);\r\n}\r\n\r\n/*# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbInNyYy9hcHAvbmF2LW1lbnUvbmF2LW1lbnUuY29tcG9uZW50LmNzcyJdLCJuYW1lcyI6W10sIm1hcHBpbmdzIjoiQUFBQTtFQUNFLG1CQUFtQjtFQUNuQixrQkFBa0I7RUFDbEIscUJBQXFCO0FBQ3ZCOztBQUVBO0VBQ0UsZUFBZTtBQUNqQjs7QUFDQTtFQUNFO0lBQ0UsZUFBZTtFQUNqQjtBQUNGOztBQUVBO0VBQ0UsOENBQThDO0FBQ2hEIiwiZmlsZSI6InNyYy9hcHAvbmF2LW1lbnUvbmF2LW1lbnUuY29tcG9uZW50LmNzcyIsInNvdXJjZXNDb250ZW50IjpbImEubmF2YmFyLWJyYW5kIHtcclxuICB3aGl0ZS1zcGFjZTogbm9ybWFsO1xyXG4gIHRleHQtYWxpZ246IGNlbnRlcjtcclxuICB3b3JkLWJyZWFrOiBicmVhay1hbGw7XHJcbn1cclxuXHJcbmh0bWwge1xyXG4gIGZvbnQtc2l6ZTogMTRweDtcclxufVxyXG5AbWVkaWEgKG1pbi13aWR0aDogNzY4cHgpIHtcclxuICBodG1sIHtcclxuICAgIGZvbnQtc2l6ZTogMTZweDtcclxuICB9XHJcbn1cclxuXHJcbi5ib3gtc2hhZG93IHtcclxuICBib3gtc2hhZG93OiAwIC4yNXJlbSAuNzVyZW0gcmdiYSgwLCAwLCAwLCAuMDUpO1xyXG59XHJcbiJdfQ== */"

/***/ }),

/***/ "./src/app/nav-menu/nav-menu.component.ts":
/*!************************************************!*\
  !*** ./src/app/nav-menu/nav-menu.component.ts ***!
  \************************************************/
/*! exports provided: NavMenuComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "NavMenuComponent", function() { return NavMenuComponent; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _angular_router__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/router */ "./node_modules/@angular/router/fesm5/router.js");
/* harmony import */ var _services_authentication_service__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ../services/authentication.service */ "./src/app/services/authentication.service.ts");
/* harmony import */ var _services_TransactionService__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! ../services/TransactionService */ "./src/app/services/TransactionService.ts");
/* harmony import */ var _angular_forms__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! @angular/forms */ "./node_modules/@angular/forms/fesm5/forms.js");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (undefined && undefined.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};





var NavMenuComponent = /** @class */ (function () {
    function NavMenuComponent(router, transactionService, authService) {
        this.router = router;
        this.transactionService = transactionService;
        this.authService = authService;
        this.isExpanded = false;
        this.showSearchBar = true;
        this.searchText = '';
        this.showSearchResult = false;
        this.tableInfo = [];
        this.contactForm = this.createFormGroup();
        this.registerForm = this.createFormGroup();
    }
    NavMenuComponent.prototype.search = function (searchString) {
        var _this = this;
        this.transactionService.search(searchString).subscribe(function (res) {
            _this.tableInfo = res;
            console.log(res);
            _this.showSearchResult = true;
        });
    };
    NavMenuComponent.prototype.ngOnInit = function () {
        this.wallet = JSON.parse(localStorage.getItem('currentUser'));
        if (this.wallet == null || this.wallet == undefined) {
            this.isLogged = false;
        }
        else {
            this.isLogged = true;
        }
    };
    NavMenuComponent.prototype.createFormGroup = function () {
        return new _angular_forms__WEBPACK_IMPORTED_MODULE_4__["FormGroup"]({
            name: new _angular_forms__WEBPACK_IMPORTED_MODULE_4__["FormControl"](),
            email: new _angular_forms__WEBPACK_IMPORTED_MODULE_4__["FormControl"](),
        });
    };
    NavMenuComponent.prototype.home = function () {
        this.router.navigate(['/']);
    };
    NavMenuComponent.prototype.facebookLogin = function () {
        this.authService.facebookLogin();
        this.router.navigate(['/wallet']);
    };
    NavMenuComponent.prototype.facebookRegister = function () {
        this.authService.facebookRegister();
        this.router.navigate(['/wallet']);
    };
    NavMenuComponent.prototype.googleLogin = function () {
        this.authService.googleLogin();
        this.router.navigate(['/wallet']);
    };
    NavMenuComponent.prototype.googleRegister = function () {
        this.authService.googleRegister();
        this.router.navigate(['/wallet']);
    };
    NavMenuComponent.prototype.logOut = function () {
        this.authService.logout();
        this.ngOnInit();
    };
    NavMenuComponent.prototype.logIn = function () {
        console.log(this.contactForm.value["name"]);
        this.authService.login(this.contactForm.value["email"], this.contactForm.value["name"]);
        // Do useful stuff with the gathered data
    };
    NavMenuComponent.prototype.register = function () {
        console.log(this.registerForm.value["name"]);
        this.authService.register(this.registerForm.value["email"], this.registerForm.value["name"]);
        // Do useful stuff with the gathered data
    };
    NavMenuComponent.prototype.collapse = function () {
        this.isExpanded = false;
    };
    NavMenuComponent.prototype.toggle = function () {
        this.isExpanded = !this.isExpanded;
    };
    NavMenuComponent = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Component"])({
            selector: 'app-nav-menu',
            template: __webpack_require__(/*! raw-loader!./nav-menu.component.html */ "./node_modules/raw-loader/index.js!./src/app/nav-menu/nav-menu.component.html"),
            styles: [__webpack_require__(/*! ./nav-menu.component.css */ "./src/app/nav-menu/nav-menu.component.css")]
        }),
        __metadata("design:paramtypes", [_angular_router__WEBPACK_IMPORTED_MODULE_1__["Router"], _services_TransactionService__WEBPACK_IMPORTED_MODULE_3__["TransactionService"], _services_authentication_service__WEBPACK_IMPORTED_MODULE_2__["AuthenticationService"]])
    ], NavMenuComponent);
    return NavMenuComponent;
}());



/***/ }),

/***/ "./src/app/services/BlockService.ts":
/*!******************************************!*\
  !*** ./src/app/services/BlockService.ts ***!
  \******************************************/
/*! exports provided: BlockService */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "BlockService", function() { return BlockService; });
/* harmony import */ var _angular_common_http__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/common/http */ "./node_modules/@angular/common/fesm5/http.js");
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (undefined && undefined.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};


var BlockService = /** @class */ (function () {
    function BlockService(http) {
        this.http = http;
        this.baseUrl = localStorage.getItem('baseUrl');
    }
    BlockService.prototype.getBlock = function () {
        var header = new _angular_common_http__WEBPACK_IMPORTED_MODULE_0__["HttpHeaders"]().set("Access-Control-Allow-Origin", "*");
        return this.http.post(this.baseUrl + "/blockchain/blocks", header);
    };
    BlockService.prototype.getBlockDetail = function (id) {
        var params = new _angular_common_http__WEBPACK_IMPORTED_MODULE_0__["HttpParams"]().set("id", id.toString());
        return this.http.post(this.baseUrl + "/blockchain/block?id=" + id, null);
    };
    BlockService = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["Injectable"])(),
        __metadata("design:paramtypes", [_angular_common_http__WEBPACK_IMPORTED_MODULE_0__["HttpClient"]])
    ], BlockService);
    return BlockService;
}());



/***/ }),

/***/ "./src/app/services/NodeService.ts":
/*!*****************************************!*\
  !*** ./src/app/services/NodeService.ts ***!
  \*****************************************/
/*! exports provided: NodeService */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "NodeService", function() { return NodeService; });
/* harmony import */ var _angular_common_http__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/common/http */ "./node_modules/@angular/common/fesm5/http.js");
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (undefined && undefined.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};


var NodeService = /** @class */ (function () {
    function NodeService(http) {
        this.http = http;
        this.baseUrl = localStorage.getItem('baseUrl');
    }
    NodeService.prototype.getNode = function () {
        var header = new _angular_common_http__WEBPACK_IMPORTED_MODULE_0__["HttpHeaders"]().set("Access-Control-Allow-Origin", "*");
        return this.http.get(this.baseUrl + "/network/nodes", { headers: header });
    };
    NodeService = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["Injectable"])(),
        __metadata("design:paramtypes", [_angular_common_http__WEBPACK_IMPORTED_MODULE_0__["HttpClient"]])
    ], NodeService);
    return NodeService;
}());



/***/ }),

/***/ "./src/app/services/TransactionService.ts":
/*!************************************************!*\
  !*** ./src/app/services/TransactionService.ts ***!
  \************************************************/
/*! exports provided: TransactionService */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "TransactionService", function() { return TransactionService; });
/* harmony import */ var _angular_common_http__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/common/http */ "./node_modules/@angular/common/fesm5/http.js");
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _Model_transactionViewModel__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ../Model/transactionViewModel */ "./src/app/Model/transactionViewModel.ts");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (undefined && undefined.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};



var TransactionService = /** @class */ (function () {
    function TransactionService(http) {
        this.http = http;
        this.baseUrl = localStorage.getItem('baseUrl');
    }
    TransactionService.prototype.getTransactions = function () {
        var header = new _angular_common_http__WEBPACK_IMPORTED_MODULE_0__["HttpHeaders"]().set("Access-Control-Allow-Origin", "*");
        return this.http.post(this.baseUrl + "/blockchain/transactions", header);
    };
    TransactionService.prototype.getTransactionPool = function () {
        var header = new _angular_common_http__WEBPACK_IMPORTED_MODULE_0__["HttpHeaders"]().set("Access-Control-Allow-Origin", "*");
        return this.http.post(this.baseUrl + "/blockchain/pool", null).subscribe({
            next: function (response) { console.log(response); },
        });
    };
    TransactionService.prototype.search = function (searchString) {
        var params = new _angular_common_http__WEBPACK_IMPORTED_MODULE_0__["HttpParams"]().set("criteria", searchString);
        return this.http.get(this.baseUrl + "/blockchain/search", { params: params });
    };
    TransactionService.prototype.sendTransaction = function (receiverId, amount, message) {
        this.t = new _Model_transactionViewModel__WEBPACK_IMPORTED_MODULE_2__["transactionViewModel"];
        this.t.amount = amount;
        this.t.receiver = receiverId;
        this.t.message = message;
        return this.http.post(this.baseUrl + "/wallet/publish", this.t);
    };
    TransactionService.prototype.decryptTransaction = function (id) {
        var params = new _angular_common_http__WEBPACK_IMPORTED_MODULE_0__["HttpParams"]()
            .set("id", id.toString());
        return this.http.get(this.baseUrl + "/wallet/decrypt", { params: params, responseType: 'text' });
    };
    TransactionService = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["Injectable"])(),
        __metadata("design:paramtypes", [_angular_common_http__WEBPACK_IMPORTED_MODULE_0__["HttpClient"]])
    ], TransactionService);
    return TransactionService;
}());



/***/ }),

/***/ "./src/app/services/WalletService.ts":
/*!*******************************************!*\
  !*** ./src/app/services/WalletService.ts ***!
  \*******************************************/
/*! exports provided: WalletService */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "WalletService", function() { return WalletService; });
/* harmony import */ var _angular_common_http__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/common/http */ "./node_modules/@angular/common/fesm5/http.js");
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _Model_User__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ../Model/User */ "./src/app/Model/User.ts");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (undefined && undefined.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};



var WalletService = /** @class */ (function () {
    function WalletService(http) {
        this.http = http;
        this.baseUrl = localStorage.getItem('baseUrl');
    }
    WalletService.prototype.createWallet = function (name, mail, token) {
        var user = new _Model_User__WEBPACK_IMPORTED_MODULE_2__["User"]();
        user.name = name;
        user.mail = mail;
        user.token = token;
        //let params = new HttpParams().set("name", name).set("mail", mail).set("token", token)
        return this.http.post(this.baseUrl + "/wallet/create", user);
    };
    WalletService.prototype.walletLoad = function (identifier) {
        var params = new _angular_common_http__WEBPACK_IMPORTED_MODULE_0__["HttpParams"]().set("identifier", identifier);
        return this.http.get(this.baseUrl + "/wallet/load", { params: params });
    };
    WalletService.prototype.walletStatus = function () {
        return this.http.get(this.baseUrl + "/wallet/status");
    };
    WalletService.prototype.walletCount = function () {
        return this.http.post(this.baseUrl + "/blockchain/wallets", null);
    };
    WalletService.prototype.walletTransaction = function () {
        return this.http.get(this.baseUrl + "/wallet/transactions");
    };
    WalletService.prototype.walletDecrypt = function (id) {
        return this.http.post(this.baseUrl + "/wallet/decrypt", id);
    };
    WalletService = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["Injectable"])(),
        __metadata("design:paramtypes", [_angular_common_http__WEBPACK_IMPORTED_MODULE_0__["HttpClient"]])
    ], WalletService);
    return WalletService;
}());



/***/ }),

/***/ "./src/app/services/authentication.service.ts":
/*!****************************************************!*\
  !*** ./src/app/services/authentication.service.ts ***!
  \****************************************************/
/*! exports provided: AuthenticationService */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "AuthenticationService", function() { return AuthenticationService; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _angular_common_http__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/common/http */ "./node_modules/@angular/common/fesm5/http.js");
/* harmony import */ var rxjs__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! rxjs */ "./node_modules/rxjs/_esm5/index.js");
/* harmony import */ var _angular_router__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! @angular/router */ "./node_modules/@angular/router/fesm5/router.js");
/* harmony import */ var _WalletService__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! .//WalletService */ "./src/app/services/WalletService.ts");
/* harmony import */ var angular5_social_login__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! angular5-social-login */ "./node_modules/angular5-social-login/angular5-social-login.umd.js");
/* harmony import */ var angular5_social_login__WEBPACK_IMPORTED_MODULE_5___default = /*#__PURE__*/__webpack_require__.n(angular5_social_login__WEBPACK_IMPORTED_MODULE_5__);
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (undefined && undefined.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};






var AuthenticationService = /** @class */ (function () {
    function AuthenticationService(http, socialAuthService, walletService, route) {
        this.http = http;
        this.socialAuthService = socialAuthService;
        this.walletService = walletService;
        this.route = route;
        this.currentUserSubject = new rxjs__WEBPACK_IMPORTED_MODULE_2__["BehaviorSubject"](JSON.parse(localStorage.getItem('currentUser')));
        this.currentUser = this.currentUserSubject.asObservable();
    }
    AuthenticationService.prototype.facebookLogin = function () {
        var _this = this;
        var socialPlatformProvider = angular5_social_login__WEBPACK_IMPORTED_MODULE_5__["FacebookLoginProvider"].PROVIDER_ID;
        this.socialAuthService.signIn(socialPlatformProvider).then(function (userData) {
            _this.walletService.walletLoad(userData.email).subscribe(function (wallet) {
                console.log(wallet.token);
                localStorage.setItem('currentUser', JSON.stringify(wallet));
                window.location.reload(true);
            });
        });
    };
    AuthenticationService.prototype.facebookRegister = function () {
        var _this = this;
        var socialPlatformProvider = angular5_social_login__WEBPACK_IMPORTED_MODULE_5__["FacebookLoginProvider"].PROVIDER_ID;
        this.socialAuthService.signIn(socialPlatformProvider).then(function (userData) {
            console.log("facebook" + userData.token);
            _this.walletService.createWallet(userData.name, userData.email, userData.token).subscribe(function (wallet) {
                console.log(wallet.token);
                localStorage.setItem('currentUser', JSON.stringify(wallet));
                window.location.reload(true);
            });
        });
    };
    AuthenticationService.prototype.googleLogin = function () {
        var _this = this;
        var socialPlatformProvider = angular5_social_login__WEBPACK_IMPORTED_MODULE_5__["GoogleLoginProvider"].PROVIDER_ID;
        this.socialAuthService.signIn(socialPlatformProvider).then(function (userData) {
            _this.walletService.walletLoad(userData.email).subscribe(function (wallet) {
                console.log(wallet.token);
                localStorage.setItem('currentUser', JSON.stringify(wallet));
                window.location.reload(true);
            });
            //this will return user data from facebook. What you need is a user token which you will send it to the server
            console.log(userData.name);
        });
    };
    AuthenticationService.prototype.googleRegister = function () {
        var _this = this;
        var socialPlatformProvider = angular5_social_login__WEBPACK_IMPORTED_MODULE_5__["GoogleLoginProvider"].PROVIDER_ID;
        this.socialAuthService.signIn(socialPlatformProvider).then(function (userData) {
            console.log("google" + userData.token);
            _this.walletService.createWallet(userData.name, userData.email, userData.token).subscribe(function (wallet) {
                console.log(wallet.token);
                localStorage.setItem('currentUser', JSON.stringify(wallet));
                window.location.reload(true);
            });
            console.log(userData.name);
        });
    };
    AuthenticationService.prototype.login = function (mail, name) {
        if (mail != null || mail != undefined) {
            this.walletService.walletLoad(mail).subscribe(function (wallet) {
                console.log(wallet.token);
                localStorage.setItem('currentUser', JSON.stringify(wallet));
                window.location.reload(true);
            });
        }
        else {
            this.walletService.walletLoad(name).subscribe(function (wallet) {
                console.log(wallet.token);
                localStorage.setItem('currentUser', JSON.stringify(wallet));
                window.location.reload(true);
            });
        }
    };
    AuthenticationService.prototype.register = function (mail, name) {
        this.walletService.createWallet(name, mail, null).subscribe(function (wallet) {
            localStorage.setItem('currentUser', JSON.stringify(wallet));
            window.location.reload(true);
        });
    };
    AuthenticationService.prototype.logout = function () {
        // remove user from local storage to log user out
        localStorage.removeItem('currentUser');
        this.route.navigate(['/']);
    };
    AuthenticationService = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Injectable"])({ providedIn: 'root' }),
        __metadata("design:paramtypes", [_angular_common_http__WEBPACK_IMPORTED_MODULE_1__["HttpClient"], angular5_social_login__WEBPACK_IMPORTED_MODULE_5__["AuthService"], _WalletService__WEBPACK_IMPORTED_MODULE_4__["WalletService"], _angular_router__WEBPACK_IMPORTED_MODULE_3__["Router"]])
    ], AuthenticationService);
    return AuthenticationService;
}());



/***/ }),

/***/ "./src/app/socialloginConfig.ts":
/*!**************************************!*\
  !*** ./src/app/socialloginConfig.ts ***!
  \**************************************/
/*! exports provided: getAuthServiceConfigs */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "getAuthServiceConfigs", function() { return getAuthServiceConfigs; });
/* harmony import */ var angular5_social_login__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! angular5-social-login */ "./node_modules/angular5-social-login/angular5-social-login.umd.js");
/* harmony import */ var angular5_social_login__WEBPACK_IMPORTED_MODULE_0___default = /*#__PURE__*/__webpack_require__.n(angular5_social_login__WEBPACK_IMPORTED_MODULE_0__);

function getAuthServiceConfigs() {
    var config = new angular5_social_login__WEBPACK_IMPORTED_MODULE_0__["AuthServiceConfig"]([
        {
            id: angular5_social_login__WEBPACK_IMPORTED_MODULE_0__["FacebookLoginProvider"].PROVIDER_ID,
            provider: new angular5_social_login__WEBPACK_IMPORTED_MODULE_0__["FacebookLoginProvider"]('2347129585338818')
        },
        {
            id: angular5_social_login__WEBPACK_IMPORTED_MODULE_0__["GoogleLoginProvider"].PROVIDER_ID,
            provider: new angular5_social_login__WEBPACK_IMPORTED_MODULE_0__["GoogleLoginProvider"]("414552174408-l9mugfpl6e0umejsf1gagdk809dddg95.apps.googleusercontent.com")
        }
    ]);
    return config;
}


/***/ }),

/***/ "./src/app/subscribe/subscribe.component.css":
/*!***************************************************!*\
  !*** ./src/app/subscribe/subscribe.component.css ***!
  \***************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "\n/*# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbXSwibmFtZXMiOltdLCJtYXBwaW5ncyI6IiIsImZpbGUiOiJzcmMvYXBwL3N1YnNjcmliZS9zdWJzY3JpYmUuY29tcG9uZW50LmNzcyJ9 */"

/***/ }),

/***/ "./src/app/subscribe/subscribe.component.ts":
/*!**************************************************!*\
  !*** ./src/app/subscribe/subscribe.component.ts ***!
  \**************************************************/
/*! exports provided: SubscribeComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "SubscribeComponent", function() { return SubscribeComponent; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (undefined && undefined.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};

var SubscribeComponent = /** @class */ (function () {
    function SubscribeComponent() {
    }
    SubscribeComponent.prototype.ngOnInit = function () {
        this.showSearchBar = false;
    };
    SubscribeComponent = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Component"])({
            selector: 'app-subscribe',
            template: __webpack_require__(/*! raw-loader!./subscribe.component.html */ "./node_modules/raw-loader/index.js!./src/app/subscribe/subscribe.component.html"),
            styles: [__webpack_require__(/*! ./subscribe.component.css */ "./src/app/subscribe/subscribe.component.css")]
        }),
        __metadata("design:paramtypes", [])
    ], SubscribeComponent);
    return SubscribeComponent;
}());



/***/ }),

/***/ "./src/app/trasaction-modal/trasaction-modal.component.css":
/*!*****************************************************************!*\
  !*** ./src/app/trasaction-modal/trasaction-modal.component.css ***!
  \*****************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "thead {\r\n  float: left;\r\n}\r\n\r\n  thead th {\r\n    display: block;\r\n  }\r\n\r\n  tbody {\r\n  float: right;\r\n}\r\n\r\n/*# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbInNyYy9hcHAvdHJhc2FjdGlvbi1tb2RhbC90cmFzYWN0aW9uLW1vZGFsLmNvbXBvbmVudC5jc3MiXSwibmFtZXMiOltdLCJtYXBwaW5ncyI6IkFBQUE7RUFDRSxXQUFXO0FBQ2I7O0VBRUU7SUFDRSxjQUFjO0VBQ2hCOztFQUVGO0VBQ0UsWUFBWTtBQUNkIiwiZmlsZSI6InNyYy9hcHAvdHJhc2FjdGlvbi1tb2RhbC90cmFzYWN0aW9uLW1vZGFsLmNvbXBvbmVudC5jc3MiLCJzb3VyY2VzQ29udGVudCI6WyJ0aGVhZCB7XHJcbiAgZmxvYXQ6IGxlZnQ7XHJcbn1cclxuXHJcbiAgdGhlYWQgdGgge1xyXG4gICAgZGlzcGxheTogYmxvY2s7XHJcbiAgfVxyXG5cclxudGJvZHkge1xyXG4gIGZsb2F0OiByaWdodDtcclxufVxyXG4iXX0= */"

/***/ }),

/***/ "./src/app/trasaction-modal/trasaction-modal.component.ts":
/*!****************************************************************!*\
  !*** ./src/app/trasaction-modal/trasaction-modal.component.ts ***!
  \****************************************************************/
/*! exports provided: TrasactionModalComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "TrasactionModalComponent", function() { return TrasactionModalComponent; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (undefined && undefined.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};

var TrasactionModalComponent = /** @class */ (function () {
    function TrasactionModalComponent( /*id: number, message: string, amount: number, sender: number, mined: boolean, timestamp: string*/) {
        this.id = localStorage.getItem('id');
        this.message = localStorage.getItem('message');
        this.amount = localStorage.getItem('amount');
        this.sender = localStorage.getItem('sender');
        this.mined = localStorage.getItem('mined');
        this.timestamp = localStorage.getItem('timestamp');
        //this.id = id;
        //this.message = message;
        //this.amount = amount;
        //this.sender = sender;
        //this.mined = mined;
        //this.timestamp = timestamp;
    }
    TrasactionModalComponent.prototype.ngOnInit = function () {
    };
    TrasactionModalComponent = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Component"])({
            selector: 'app-trasaction-modal',
            template: __webpack_require__(/*! raw-loader!./trasaction-modal.component.html */ "./node_modules/raw-loader/index.js!./src/app/trasaction-modal/trasaction-modal.component.html"),
            template: "\n    <div class=\"modal-header\">\n      <h2 class=\"modal-title\">d\u00E9chiffrement  transaction</h2>\n    \n    </div>\n    <div class=\"modal-body\">\n      <h5>Identifiant: {{id}}</h5>\n      <h5>Montant: {{amount}}</h5>\n      <h5>Lanceur: {{sender}}</h5>\n      <h5>Min\u00E9e: {{mined}}</h5>\n      <h5>Timestamp: {{timestamp}}</h5>\n      <h5>Message: {{message}}</h5>\n    </div>\n  ",
            styles: [__webpack_require__(/*! ./trasaction-modal.component.css */ "./src/app/trasaction-modal/trasaction-modal.component.css")]
        }),
        __metadata("design:paramtypes", [])
    ], TrasactionModalComponent);
    return TrasactionModalComponent;
}());



/***/ }),

/***/ "./src/app/wallet/wallet.component.css":
/*!*********************************************!*\
  !*** ./src/app/wallet/wallet.component.css ***!
  \*********************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "\n/*# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbXSwibmFtZXMiOltdLCJtYXBwaW5ncyI6IiIsImZpbGUiOiJzcmMvYXBwL3dhbGxldC93YWxsZXQuY29tcG9uZW50LmNzcyJ9 */"

/***/ }),

/***/ "./src/app/wallet/wallet.component.ts":
/*!********************************************!*\
  !*** ./src/app/wallet/wallet.component.ts ***!
  \********************************************/
/*! exports provided: WalletComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "WalletComponent", function() { return WalletComponent; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _services_WalletService__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ../services/WalletService */ "./src/app/services/WalletService.ts");
/* harmony import */ var _services_TransactionService__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ../services/TransactionService */ "./src/app/services/TransactionService.ts");
/* harmony import */ var _angular_router__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! @angular/router */ "./node_modules/@angular/router/fesm5/router.js");
/* harmony import */ var _ng_bootstrap_ng_bootstrap__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! @ng-bootstrap/ng-bootstrap */ "./node_modules/@ng-bootstrap/ng-bootstrap/fesm5/ng-bootstrap.js");
/* harmony import */ var _trasaction_modal_trasaction_modal_component__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! ../trasaction-modal/trasaction-modal.component */ "./src/app/trasaction-modal/trasaction-modal.component.ts");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (undefined && undefined.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};






var WalletComponent = /** @class */ (function () {
    function WalletComponent(walletService, transactionService, router, activeModal, modalService) {
        this.walletService = walletService;
        this.transactionService = transactionService;
        this.router = router;
        this.activeModal = activeModal;
        this.modalService = modalService;
        this.sendedTransactions = [];
        this.receivedTransactions = [];
        this.message = "yo";
    }
    WalletComponent.prototype.ngOnInit = function () {
        // Fetch the loaded wallet on the blockchain
        this.wallet = JSON.parse(localStorage.getItem('currentUser'));
        if (this.wallet == null || this.wallet == undefined) {
            this.router.navigate(['/']);
        }
        else {
            this.loadTransactions();
        }
    };
    //Fetch the transaction associated to the wallet
    WalletComponent.prototype.loadTransactions = function () {
        var _this = this;
        this.walletService.walletTransaction()
            .subscribe(function (transactions) {
            // Add all the transactions retreived
            transactions.forEach(function (transaction) {
                // Sort the transaction (received or sended)
                if (transaction.receiverId == _this.wallet.id) {
                    _this.receivedTransactions.push(transaction);
                }
                else {
                    _this.sendedTransactions.push(transaction);
                }
            });
        }, function (error) {
            // When receive an error
            console.log("Error");
            _this.dataLoaded = Promise.resolve(false);
        }, function () {
            // At the end of the load
            _this.dataLoaded = Promise.resolve(true);
        });
    };
    WalletComponent.prototype.createTransaction = function () {
        var _this = this;
        console.log(this.trReceiverId, this.trAmount, this.trMessage);
        this.transactionService.sendTransaction(this.trReceiverId, this.trAmount, this.trMessage)
            .subscribe(function (result) {
            if (result == null) {
                _this.showAlert = true;
            }
            else {
                window.location.reload(true);
            }
        });
    };
    WalletComponent.prototype.decryptTransaction = function (id, amount, sId, mined, timestamp) {
        var _this = this;
        localStorage.setItem('id', id.toString());
        localStorage.setItem('amount', amount.toString());
        localStorage.setItem('sender', sId.toString());
        if (mined == true) {
            localStorage.setItem('mined', 'true');
        }
        else {
            localStorage.setItem('mined', 'false');
        }
        localStorage.setItem('timestamp', timestamp);
        this.transactionService.decryptTransaction(id).subscribe(function (result) {
            localStorage.setItem('message', result);
            var modalRef = _this.modalService.open(_trasaction_modal_trasaction_modal_component__WEBPACK_IMPORTED_MODULE_5__["TrasactionModalComponent"], { size: 'lg', backdrop: 'static' });
            modalRef.componentInstance.name = 'transaction';
        });
    };
    WalletComponent = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Component"])({
            selector: 'app-wallet',
            template: __webpack_require__(/*! raw-loader!./wallet.component.html */ "./node_modules/raw-loader/index.js!./src/app/wallet/wallet.component.html"),
            styles: [__webpack_require__(/*! ./wallet.component.css */ "./src/app/wallet/wallet.component.css")]
        }),
        __metadata("design:paramtypes", [_services_WalletService__WEBPACK_IMPORTED_MODULE_1__["WalletService"],
            _services_TransactionService__WEBPACK_IMPORTED_MODULE_2__["TransactionService"], _angular_router__WEBPACK_IMPORTED_MODULE_3__["Router"], _ng_bootstrap_ng_bootstrap__WEBPACK_IMPORTED_MODULE_4__["NgbActiveModal"], _ng_bootstrap_ng_bootstrap__WEBPACK_IMPORTED_MODULE_4__["NgbModal"]])
    ], WalletComponent);
    return WalletComponent;
}());



/***/ }),

/***/ "./src/environments/environment.ts":
/*!*****************************************!*\
  !*** ./src/environments/environment.ts ***!
  \*****************************************/
/*! exports provided: environment */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "environment", function() { return environment; });
// This file can be replaced during build by using the `fileReplacements` array.
// `ng build ---prod` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.
var environment = {
    production: false
};
/*
 * In development mode, to ignore zone related error stack frames such as
 * `zone.run`, `zoneDelegate.invokeTask` for easier debugging, you can
 * import the following file, but please comment it out in production mode
 * because it will have performance impact when throw error
 */
// import 'zone.js/dist/zone-error';  // Included with Angular CLI.


/***/ }),

/***/ "./src/main.ts":
/*!*********************!*\
  !*** ./src/main.ts ***!
  \*********************/
/*! exports provided: getBaseUrl */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "getBaseUrl", function() { return getBaseUrl; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _angular_platform_browser_dynamic__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/platform-browser-dynamic */ "./node_modules/@angular/platform-browser-dynamic/fesm5/platform-browser-dynamic.js");
/* harmony import */ var _app_app_module__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ./app/app.module */ "./src/app/app.module.ts");
/* harmony import */ var _environments_environment__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! ./environments/environment */ "./src/environments/environment.ts");




function getBaseUrl() {
    return document.getElementsByTagName('base')[0].href;
}
var providers = [
    { provide: 'BASE_URL', useFactory: getBaseUrl, deps: [] }
];
if (_environments_environment__WEBPACK_IMPORTED_MODULE_3__["environment"].production) {
    Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["enableProdMode"])();
}
Object(_angular_platform_browser_dynamic__WEBPACK_IMPORTED_MODULE_1__["platformBrowserDynamic"])(providers).bootstrapModule(_app_app_module__WEBPACK_IMPORTED_MODULE_2__["AppModule"])
    .catch(function (err) { return console.log(err); });


/***/ }),

/***/ 0:
/*!***************************!*\
  !*** multi ./src/main.ts ***!
  \***************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

module.exports = __webpack_require__(/*! /home/aps/Workspace/03_Supinfo/05_PJT/4-PJT/website/src/main.ts */"./src/main.ts");


/***/ })

},[[0,"runtime","vendor"]]]);
//# sourceMappingURL=main.js.map