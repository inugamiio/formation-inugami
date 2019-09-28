// ANGULAR CORE MODULES --------------------------------------------------------
import {NgModule}                       from '@angular/core';
import {RouterModule}                   from '@angular/router';
import {CommonModule}                   from '@angular/common';
import {BrowserModule}                  from '@angular/platform-browser';
import {HttpClientModule}               from '@angular/common/http';
import {ReactiveFormsModule}            from '@angular/forms';
import {FormsModule}                    from '@angular/forms';

// INUGAMI CORE ----------------------------------------------------------------
import {AppRootModule}                  from 'js/app/app.root.module';
// EXAMPLE PLUGIN ----------------------------------------------------------------
import {ExampleHomeView}                from './views/example.home.view';
import {ExampleDashboardView}           from './views/example.dashboard.view';

import {PluginMenuService}              from './services/plugin.menu.service';

// MODULE ----------------------------------------------------------------------
@NgModule({
    imports         : [CommonModule,
                       BrowserModule,
                       ReactiveFormsModule,
                       FormsModule,
                       HttpClientModule,
                       RouterModule,
                       AppRootModule],
    declarations    : [ExampleHomeView,ExampleDashboardView],
    entryComponents : [],
    exports         : [],
    providers       : [PluginMenuService],
    bootstrap       : []
})
export class ExamplePluginModule {

}
export const InugamiPluginDashboardDemoRoutes = RouterModule.forRoot([
    {path: 'example'           , component: ExampleHomeView},
    {path: 'example/home'      , component: ExampleHomeView},
    {path: 'example/dashboard' , component: ExampleDashboardView}
]);

