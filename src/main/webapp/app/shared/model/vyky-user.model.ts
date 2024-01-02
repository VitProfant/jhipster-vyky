import dayjs from 'dayjs';
import { Role } from 'app/shared/model/enumerations/role.model';

export interface IVykyUser {
  id?: number;
  version?: number;
  login?: string;
  password?: string;
  email?: string;
  role?: keyof typeof Role;
  messagesReadAt?: dayjs.Dayjs | null;
  reactionsReadAt?: dayjs.Dayjs | null;
  lastActivity?: dayjs.Dayjs | null;
  createdAt?: dayjs.Dayjs;
  updatedAt?: dayjs.Dayjs | null;
  deletedAt?: dayjs.Dayjs | null;
}

export const defaultValue: Readonly<IVykyUser> = {};
