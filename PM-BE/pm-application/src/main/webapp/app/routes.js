import { authenticated, authorized } from './shared/guard/auth';
import { ADMIN, USER } from './config/authority';

const notAuthenticated = {
    name: 'Not authenticated',
    component: 'app-not-authenticated',
    resolve: () => import('./pages/auth/NotAuthenticated')
};
const notAuthorized = {
    name: 'Not authorized',
    component: 'app-not-authorized',
    resolve: () => import('./pages/auth/NotAuthorized')
};

const routes = [
    // {
    //     name: 'cv-template',
    //     pattern: '',
    //     data: {},
    //     component: 'cv-template',
    //     resolve: () => import('./pages/cv/CVTemplate')
    // },
    {
        name: 'homepage',
        pattern: 'home',
        data: {},
        component: 'app-homepage',
        resolve: () => import('./pages/homepage/Homepage')
    },
    {
        name: 'account-setting',
        pattern: 'account',
        data: {},
        component: 'app-account-setting',
        resolve: () => import('./pages/account-setting/AccountSetting'),
        authentication: {
            authenticate: authenticated,
            unauthenticated: notAuthenticated
        }
    },

    {
        name: 'intro',
        pattern: '',
        data: {},
        component: 'app-intro',
        resolve: () => import('./pages/intro/Intro')
    },
    {
        name: 'profile',
        pattern: 'profile',
        data: {
            title: 'Profile'
        },
        component: 'app-profile',
        resolve: () => import('./pages/profile/Profile'),
        authentication: {
            authenticate: authenticated,
            unauthenticated: notAuthenticated
        }
    },
    {
        name: 'edit-profile',
        pattern: 'edit-profile',
        data: {
            title: 'Edit Profile'
        },
        component: 'app-edit-profile',
        resolve: () => import('./pages/edit-profile/EditProfile'),
        authentication: {
            authenticate: authenticated,
            unauthenticated: notAuthenticated
        }
    },
    {
        name: 'not-found',
        pattern: '*',
        data: {
            title: 'Not Found'
        },
        component: 'app-not-found',
        resolve: () => import('./pages/not-found/NotFound')
    }
];

export default routes;
