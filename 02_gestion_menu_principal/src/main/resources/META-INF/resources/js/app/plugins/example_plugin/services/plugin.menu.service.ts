import {Injectable} from '@angular/core';
import {MainMenuService} from 'js/app/components/main_menu/main.menu.service';
import {MainMenuLink} from 'js/app/components/main_menu/main.menu.link';

Injectable()
export class PluginMenuService{

    constructor(private mainMenuService:MainMenuService){

    }

    public buildMenu(){
        this.mainMenuService.cleanLinks();
        this.mainMenuService.setCurrentTitle("Example Plugin");
        this.mainMenuService.addSubLink(new MainMenuLink("Plugin Home", "/example", "home-plugin", true));
    }
}