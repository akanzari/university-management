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
    defaultLabel: 'Gestion des utilisateurs',
    icon: 'user',
    routerLink: ['/users'],
    appAuthz: '',
  },
  {
    code: '2',
    defaultLabel: 'Gestion des etudiants',
    icon: 'user',
    routerLink: ['/students'],
    appAuthz: '',
  },
  {
    code: '3',
    defaultLabel: 'Gestion des enseignants',
    icon: 'note',
    routerLink: ['/teachers'],
    appAuthz: '',
  },
  {
    code: '4',
    defaultLabel: 'Gestion des classes',
    icon: 'wifi',
    routerLink: ['/classes'],
    appAuthz: ''
  },
  {
    code: '5',
    defaultLabel: 'Gestion des modules',
    icon: 'settings',
    routerLink: ['/modules'],
    appAuthz: '',
  },
  {
    code: '6',
    defaultLabel: 'Gestion des salles',
    icon: 'settings',
    routerLink: ['/rooms'],
    appAuthz: '',
  },
  {
    code: '7',
    defaultLabel: 'Gestion des epreuves',
    icon: 'settings',
    routerLink: ['/trials'],
    appAuthz: '',
  },
  {
    code: '8',
    defaultLabel: 'Gestion des salles dispo',
    icon: 'settings',
    routerLink: ['/available-rooms'],
    appAuthz: '',
  },
  {
    code: '9',
    defaultLabel: 'Gestion des examens',
    icon: 'settings',
    routerLink: ['/exams'],
    appAuthz: '',
  }
];