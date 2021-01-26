import { NgModule, Optional, SkipSelf, ModuleWithProviders } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { LayoutService, TitleService, TokenService, IAMService, ClassService, RoomService, RefService, StudentService, 
  TeacherService, ModuleService, ExamService, ExcelService } from './services';
import { DatePipe } from '@angular/common';
import { DisponibilityService } from './services/disponibility.service';
import { AuthzGuardService } from './guards/authz-guard.guard';


@NgModule({
  imports: [
    HttpClientModule
  ]
})
export class CoreModule {
  constructor(@Optional() @SkipSelf() parentModule: CoreModule) {
    if (parentModule) {
      throw new Error('CoreModule has already been loaded. Import Core modules in the AppModule only.');
    }
  }

  public static forRoot(): ModuleWithProviders<CoreModule> {
    return {
      ngModule: CoreModule,
      providers: [
        TitleService,
        TokenService,
        LayoutService,
        RefService,
        IAMService,
        ClassService,
        RoomService,
        StudentService,
        TeacherService,
        ModuleService,
        ExamService,
        DisponibilityService,
        ExcelService,
        AuthzGuardService,
        DatePipe
      ]
    };
  }

}