import { SideBarMenu } from './core/models';

export const subMenuItems: SideBarMenu[] = [,
  {
    code: '0',
    defaultLabel: 'Accueil',
    icon: 'bar_chart',
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
    icon: 'user_delete',
    routerLink: ['/teachers'],
    appAuthz: ''
  },
  {
    code: '49',
    defaultLabel: 'Liste des étudiants',
    icon: 'user',
    routerLink: ['/students'],
    appAuthz: ''
  },
  {
    code: '4',
    defaultLabel: 'Liste des classes',
    icon: 'form',
    routerLink: ['/classes'],
    appAuthz: ''
  },
  {
    code: '5',
    defaultLabel: 'Liste des modules',
    icon: 'copy',
    routerLink: ['/modules'],
    appAuthz: '',
  },
  {
    code: '6',
    defaultLabel: 'Liste des salles',
    icon: 'home',
    routerLink: ['/rooms'],
    appAuthz: '',
  },
  {
    code: '7',
    defaultLabel: 'Liste des epreuves',
    icon: 'indent',
    routerLink: ['/exams'],
    appAuthz: '',
  },
  {
    code: '8',
    defaultLabel: 'Calendrier',
    icon: 'calendar',
    routerLink: ['/calendars'],
    appAuthz: ''
  }
];