// ANGULAR CORE MODULES --------------------------------------------------------
import {Component, OnInit}                     from '@angular/core';

import {SessionScope} from 'js/app/scopes/session.scope';
import {PluginMenuService} from './../services/plugin.menu.service';

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
              private pluginMenuService : PluginMenuService){
  }

  ngOnInit() {
    this.session.openMainMenu();
    this.pluginMenuService.buildMenu();
    
  }


  toggleMenu(){
    this.session.toggleMainMenu();
  }
}