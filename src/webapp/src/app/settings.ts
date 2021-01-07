import { SideBarMenu } from './core/models';

export const subMenuItems: SideBarMenu[] = [,
  {
    code: '0',
    defaultLabel: 'Accueil',
    icon: 'home',
    routerLink: ['/'],
    appAuthz: '',
  },
  {
    code: '1',
    defaultLabel: 'Les utilisateurs',
    icon: 'users',
    routerLink: ['/users'],
    appAuthz: '',
  },
  {
    code: '4',
    defaultLabel: 'Les classes',
    icon: 'wifi',
    routerLink: ['/classes'],
    appAuthz: ''
  },
  {
    code: '5',
    defaultLabel: 'Les modules',
    icon: 'settings',
    routerLink: ['/modules'],
    appAuthz: '',
  },
  {
    code: '6',
    defaultLabel: 'Les salles',
    icon: 'settings',
    routerLink: ['/rooms'],
    appAuthz: '',
  },
  {
    code: '7',
    defaultLabel: 'Les epreuves',
    icon: 'settings',
    routerLink: ['/trials'],
    appAuthz: '',
  },
  {
    code: '9',
    defaultLabel: 'Les examens',
    icon: 'settings',
    routerLink: ['/exams'],
    appAuthz: '',
  }
];