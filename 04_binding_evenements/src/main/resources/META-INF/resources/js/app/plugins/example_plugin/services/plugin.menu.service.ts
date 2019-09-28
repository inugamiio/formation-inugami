import {Injectable} from '@angular/core';
import {MainMenuService} from 'js/app/components/main_menu/main.menu.service.ts';
import {MainMenuLink} from 'js/app/components/main_menu/main.menu.link';


import {SessionScope} from 'js/app/scopes/session.scope';


@Injectable()
export class PluginMenuService{

    constructor(private session:SessionScope,
                private mainMenuService:MainMenuService){
    }

    public buildMenu(){
        this.mainMenuService.cleanLinks();
        this.mainMenuService.setCurrentTitle("Example Plugin");
        this.mainMenuService.addSubLink(new MainMenuLink("Plugin Home", "/example", "home-plugin", true));
        this.mainMenuService.addSubLink(new MainMenuLink("Dashboard", "/example/dashboard", "home-plugin", true));
        
    }
}