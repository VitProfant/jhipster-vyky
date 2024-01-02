import dayjs from 'dayjs';

export interface ICandidate {
  id?: number;
  login?: string;
  password?: string;
  email?: string;
  token?: string;
  createdAt?: dayjs.Dayjs;
  deletedAt?: dayjs.Dayjs | null;
}

export const defaultValue: Readonly<ICandidate> = {};
