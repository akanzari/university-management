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
    defaultLabel: 'Liste des utilisateurs',
    icon: 'users',
    routerLink: ['/users'],
    appAuthz: '',
  },
  {
    code: '44',
    defaultLabel: 'Liste des enseignants',
    icon: 'wifi',
    routerLink: ['/teachers'],
    appAuthz: ''
  },
  {
    code: '4',
    defaultLabel: 'Liste des classes',
    icon: 'wifi',
    routerLink: ['/classes'],
    appAuthz: ''
  },
  {
    code: '5',
    defaultLabel: 'Liste des modules',
    icon: 'settings',
    routerLink: ['/modules'],
    appAuthz: '',
  },
  {
    code: '6',
    defaultLabel: 'Liste des salles',
    icon: 'settings',
    routerLink: ['/rooms'],
    appAuthz: '',
  },
  {
    code: '7',
    defaultLabel: 'Liste des epreuves',
    icon: 'settings',
    routerLink: ['/exams'],
    appAuthz: '',
  }
];