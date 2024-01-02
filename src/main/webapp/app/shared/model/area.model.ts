import dayjs from 'dayjs';
import { Role } from 'app/shared/model/enumerations/role.model';

export interface IArea {
  id?: number;
  version?: number;
  level?: number;
  name?: string;
  minimalRole?: keyof typeof Role | null;
  parentId?: number | null;
  createdAt?: dayjs.Dayjs;
  updatedAt?: dayjs.Dayjs | null;
  deletedAt?: dayjs.Dayjs | null;
  parent?: IArea | null;
}

export const defaultValue: Readonly<IArea> = {};
