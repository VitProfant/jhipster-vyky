import dayjs from 'dayjs';
import { IArea } from 'app/shared/model/area.model';
import { IVykyUser } from 'app/shared/model/vyky-user.model';
import { Role } from 'app/shared/model/enumerations/role.model';
import { PostStatus } from 'app/shared/model/enumerations/post-status.model';

export interface ITopic {
  id?: number;
  version?: number;
  name?: string;
  description?: string;
  minimalRole?: keyof typeof Role | null;
  status?: keyof typeof PostStatus;
  createdAt?: dayjs.Dayjs;
  updatedAt?: dayjs.Dayjs | null;
  deletedAt?: dayjs.Dayjs | null;
  area?: IArea | null;
  vykyUser?: IVykyUser | null;
}

export const defaultValue: Readonly<ITopic> = {};
