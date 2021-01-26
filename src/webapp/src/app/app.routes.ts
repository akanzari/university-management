import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthzGuardService } from './core/guards/authz-guard.guard';
import { HomeComponent } from './pages/home/home.component';

const routes: Routes = [
  {
    path: '', component: HomeComponent, data: { title: 'Acceuil' }
  },
  {
    path: 'users', loadChildren: () => import('./pages/users/users.module').then(m => m.UsersModule), data: { title: 'Esprit - Liste des utilisateurs' }
  },
  {
    path: 'students', loadChildren: () => import('./pages/students/students.module').then(m => m.StudentsModule), data: { title: 'Esprit - Liste des etudiants' }
  },
  {
    path: 'teachers', loadChildren: () => import('./pages/teachers/teachers.module').then(m => m.TeachersModule), data: { title: 'Esprit - Liste Enseignants' }
  },
  {
    path: 'classes', loadChildren: () => import('./pages/classes/classes.module').then(m => m.ClassesModule), data: { title: 'Esprit - Liste des classes' }
  },
  {
    path: 'modules', loadChildren: () => import('./pages/modules/modules.module').then(m => m.ModulesModule), data: { title: 'Esprit - Liste des modules' }
  },
  {
    path: 'rooms', loadChildren: () => import('./pages/rooms/rooms.module').then(m => m.RoomsModule), data: { title: 'Esprit - Liste des salles' }
  },
  {
    path: 'exams', loadChildren: () => import('./pages/exams/exams.module').then(m => m.ExamsModule), data: { title: 'Esprit - Liste des epreuves' }
  },
  {
    path: 'calendars', loadChildren: () => import('./pages/calendars/calendar.module').then(m => m.CalendarsModule), data: { title: 'Esprit - Calendries' }
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}