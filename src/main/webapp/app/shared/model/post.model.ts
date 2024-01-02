import dayjs from 'dayjs';
import { ITopic } from 'app/shared/model/topic.model';
import { IVykyUser } from 'app/shared/model/vyky-user.model';
import { PostStatus } from 'app/shared/model/enumerations/post-status.model';
import { Role } from 'app/shared/model/enumerations/role.model';

export interface IPost {
  id?: number;
  version?: number;
  level?: number;
  title?: string;
  content?: string;
  status?: keyof typeof PostStatus;
  minimalRole?: keyof typeof Role | null;
  parentId?: number | null;
  upvotes?: number | null;
  downvotes?: number | null;
  createdAt?: dayjs.Dayjs;
  updatedAt?: dayjs.Dayjs | null;
  deletedAt?: dayjs.Dayjs | null;
  topic?: ITopic | null;
  parent?: IPost | null;
  author?: IVykyUser | null;
}

export const defaultValue: Readonly<IPost> = {};
