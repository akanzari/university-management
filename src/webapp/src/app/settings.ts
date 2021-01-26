import { SideBarMenu } from './core/models';

export const subMenuItems: SideBarMenu[] = [,
  {
    code: '0',
    defaultLabel: 'Accueil',
    icon: 'bar_chart',
    routerLink: ['/'],
    appAuthz: 'HOME',
  },
  {
    code: '1',
    defaultLabel: 'Liste des utilisateurs',
    icon: 'users',
    routerLink: ['/users'],
    appAuthz: 'USERS',
  },
  {
    code: '44',
    defaultLabel: 'Liste des enseignants',
    icon: 'user_delete',
    routerLink: ['/teachers'],
    appAuthz: 'TEACHERS'
  },
  {
    code: '49',
    defaultLabel: 'Liste des étudiants',
    icon: 'user',
    routerLink: ['/students'],
    appAuthz: 'STUDENTS'
  },
  {
    code: '4',
    defaultLabel: 'Liste des classes',
    icon: 'form',
    routerLink: ['/classes'],
    appAuthz: 'CLASSES'
  },
  {
    code: '5',
    defaultLabel: 'Liste des modules',
    icon: 'copy',
    routerLink: ['/modules'],
    appAuthz: 'MODULES',
  },
  {
    code: '6',
    defaultLabel: 'Liste des salles',
    icon: 'home',
    routerLink: ['/rooms'],
    appAuthz: 'ROOMS',
  },
  {
    code: '7',
    defaultLabel: 'Liste des epreuves',
    icon: 'indent',
    routerLink: ['/exams'],
    appAuthz: 'EXAMS',
  },
  {
    code: '8',
    defaultLabel: 'Calendriers',
    icon: 'calendar',
    routerLink: ['/calendars'],
    appAuthz: 'CALENDARS',
    subMenus: [
      {
        code: '81',
        defaultLabel: 'Enseignants',
        icon: 'calendar',
        routerLink: ['/calendars/teachers'],
        appAuthz: 'CALENDARTeacher'
      },
      {
        code: '82',
        defaultLabel: 'Classes',
        icon: 'calendar',
        routerLink: ['/calendars/classes'],
        appAuthz: 'CALENDARClass'
      },
      {
        code: '84',
        defaultLabel: 'Salles',
        icon: 'calendar',
        routerLink: ['/calendars/rooms'],
        appAuthz: 'CALENDARRoom'
      },
      {
        code: '8',
        defaultLabel: 'Etudiants',
        icon: 'calendar',
        routerLink: ['/calendars/students'],
        appAuthz: 'CALENDARSTUDENT'
      }
    ]
  }
];