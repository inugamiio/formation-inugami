// ANGULAR CORE MODULES --------------------------------------------------------
import {Component, OnInit}                     from '@angular/core';

import {SessionScope}       from 'js/app/scopes/session.scope';
import {PluginMenuService}  from './../services/plugin.menu.service';
import {HttpServices}       from 'js/app/services/http/http.services';
import {MainMenuService}    from 'js/app/components/main_menu/main.menu.service';


export const PLUGIN_MENU = {
  title:"Demo plugin"
}

// SERVICES --------------------------------------------------------------------
@Component({
    templateUrl: 'js/app/plugins/example_plugin/views/example.home.view.html',
    directives : [
    ]
})
export class ExampleHomeView implements OnInit{
  /**************************************************************************
  * ATTRIBUTES
  **************************************************************************/



  /**************************************************************************
  * CONSTRUCTORS
  **************************************************************************/
  constructor(private session:SessionScope,
              private http:HttpServices,
              private menuService:MainMenuService,
              private pluginMenuService : PluginMenuService){
                console.log("menu :",this.menuService);
  }

  ngOnInit() {
    this.session.openMainMenu();
    this.pluginMenuService.buildMenu();

  }


  toggleMenu(){
    this.session.toggleMainMenu();
  }
}