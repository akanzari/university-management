export enum UserRole {
    ADMIN    = 'ADMIN',
    AGENT = 'AGENT',
    TEACHER = 'TEACHER',
    STUDENT = 'STUDENT'
}

export enum UserActions {
    HOME = 'HOME',
    USERS = 'USERS',
    TEACHERS = 'TEACHERS',
    STUDENTS = 'STUDENTS',
    CLASSES = 'CLASSES',
    MODULES = 'MODULES',
    ROOMS = 'ROOMS',
    EXAMS = 'EXAMS',
    CALENDARS = 'CALENDARS'
}

export const RolesMatrix = {
    HOME: [UserRole.ADMIN, UserRole.AGENT],
    USERS: [UserRole.ADMIN],
    TEACHERS: [UserRole.ADMIN, UserRole.AGENT],
    STUDENTS: [UserRole.ADMIN, UserRole.AGENT],
    CLASSES: [UserRole.ADMIN, UserRole.AGENT],
    MODULES: [UserRole.ADMIN, UserRole.AGENT],
    ROOMS: [UserRole.ADMIN, UserRole.AGENT],
    EXAMS: [UserRole.ADMIN, UserRole.AGENT, UserRole.STUDENT, UserRole.TEACHER],
    CALENDARS: [UserRole.ADMIN, UserRole.AGENT, UserRole.STUDENT, UserRole.TEACHER],
    CALENDARClass: [UserRole.ADMIN, UserRole.AGENT],
    CALENDARRoom: [UserRole.ADMIN, UserRole.AGENT],
    CALENDARTeacher: [UserRole.ADMIN, UserRole.AGENT, UserRole.TEACHER],
    CALENDARSTUDENT: [UserRole.STUDENT],
}