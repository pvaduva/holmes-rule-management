/*
 Copyright 2018 ZTE Corporation.

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
*/
import { ModuleWithProviders } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AlarmRule } from './correlation-ruleList/alarmRule.component';
import { RuleInfo } from './correlation-ruleInfo/ruleInfo.component';
const appRoutes: Routes = [
    {
        path: 'alarmRule',
        component: AlarmRule
    },
    {
        path: 'ruleInfo',
        component: RuleInfo
    },
    {
        path: 'ruleInfo/:id',
        component: RuleInfo
    },
    {
        path: '',
        redirectTo: 'alarmRule',
        pathMatch: 'full'
    },

];
export const routing: ModuleWithProviders = RouterModule.forRoot(appRoutes, { useHash: true });
