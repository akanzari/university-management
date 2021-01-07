import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';

const routes: Routes = [
  {
    path: '', component: HomeComponent, data: { title: 'Acceuil' }
  },
  {
    path: 'users', loadChildren: () => import('./pages/users/users.module').then(m => m.UsersModule), data: { title: 'Gestion des utilisateurs' }
  },
  {
    path: 'students', loadChildren: () => import('./pages/students/students.module').then(m => m.StudentsModule), data: { title: 'Gestion des etudiants' }
  },
  {
    path: 'teachers', loadChildren: () => import('./pages/teachers/teachers.module').then(m => m.TeachersModule), data: { title: 'Gestion Enseignants' }
  },
  {
    path: 'classes', loadChildren: () => import('./pages/classes/classes.module').then(m => m.ClassesModule), data: { title: 'Gestion des classes' }
  },
  {
    path: 'modules', loadChildren: () => import('./pages/modules/modules.module').then(m => m.ModulesModule), data: { title: 'Gestion des modules' }
  },
  {
    path: 'rooms', loadChildren: () => import('./pages/rooms/rooms.module').then(m => m.RoomsModule), data: { title: 'Gestion des salles' }
  },
  {
    path: 'trials', loadChildren: () => import('./pages/trials/trials.module').then(m => m.TrialsModule), data: { title: 'Gestion des epreuves' }
  },
  {
    path: 'exams', loadChildren: () => import('./pages/exams/exams.module').then(m => m.ExamsModule), data: { title: 'Gestion des examens' }
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}