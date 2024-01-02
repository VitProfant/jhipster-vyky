import axios from 'axios';
import { createAsyncThunk, isFulfilled, isPending } from '@reduxjs/toolkit';
import { ASC } from 'app/shared/util/pagination.constants';
import { cleanEntity } from 'app/shared/util/entity-utils';
import { IQueryParams, createEntitySlice, EntityState, serializeAxiosError } from 'app/shared/reducers/reducer.utils';
import { IAvatar, defaultValue } from 'app/shared/model/avatar.model';

const initialState: EntityState<IAvatar> = {
  loading: false,
  errorMessage: null,
  entities: [],
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

const apiUrl = 'api/avatars';

// Actions

export const getEntities = createAsyncThunk('avatar/fetch_entity_list', async ({ sort }: IQueryParams) => {
  const requestUrl = `${apiUrl}?${sort ? `sort=${sort}&` : ''}cacheBuster=${new Date().getTime()}`;
  return axios.get<IAvatar[]>(requestUrl);
});

export const getEntity = createAsyncThunk(
  'avatar/fetch_entity',
  async (id: string | number) => {
    const requestUrl = `${apiUrl}/${id}`;
    return axios.get<IAvatar>(requestUrl);
  },
  { serializeError: serializeAxiosError },
);

export const createEntity = createAsyncThunk(
  'avatar/create_entity',
  async (entity: IAvatar, thunkAPI) => {
    const result = await axios.post<IAvatar>(apiUrl, cleanEntity(entity));
    thunkAPI.dispatch(getEntities({}));
    return result;
  },
  { serializeError: serializeAxiosError },
);

export const updateEntity = createAsyncThunk(
  'avatar/update_entity',
  async (entity: IAvatar, thunkAPI) => {
    const result = await axios.put<IAvatar>(`${apiUrl}/${entity.id}`, cleanEntity(entity));
    thunkAPI.dispatch(getEntities({}));
    return result;
  },
  { serializeError: serializeAxiosError },
);

export const partialUpdateEntity = createAsyncThunk(
  'avatar/partial_update_entity',
  async (entity: IAvatar, thunkAPI) => {
    const result = await axios.patch<IAvatar>(`${apiUrl}/${entity.id}`, cleanEntity(entity));
    thunkAPI.dispatch(getEntities({}));
    return result;
  },
  { serializeError: serializeAxiosError },
);

export const deleteEntity = createAsyncThunk(
  'avatar/delete_entity',
  async (id: string | number, thunkAPI) => {
    const requestUrl = `${apiUrl}/${id}`;
    const result = await axios.delete<IAvatar>(requestUrl);
    thunkAPI.dispatch(getEntities({}));
    return result;
  },
  { serializeError: serializeAxiosError },
);

// slice

export const AvatarSlice = createEntitySlice({
  name: 'avatar',
  initialState,
  extraReducers(builder) {
    builder
      .addCase(getEntity.fulfilled, (state, action) => {
        state.loading = false;
        state.entity = action.payload.data;
      })
      .addCase(deleteEntity.fulfilled, state => {
        state.updating = false;
        state.updateSuccess = true;
        state.entity = {};
      })
      .addMatcher(isFulfilled(getEntities), (state, action) => {
        const { data } = action.payload;

        return {
          ...state,
          loading: false,
          entities: data.sort((a, b) => {
            if (!action.meta?.arg?.sort) {
              return 1;
            }
            const order = action.meta.arg.sort.split(',')[1];
            const predicate = action.meta.arg.sort.split(',')[0];
            return order === ASC ? (a[predicate] < b[predicate] ? -1 : 1) : b[predicate] < a[predicate] ? -1 : 1;
          }),
        };
      })
      .addMatcher(isFulfilled(createEntity, updateEntity, partialUpdateEntity), (state, action) => {
        state.updating = false;
        state.loading = false;
        state.updateSuccess = true;
        state.entity = action.payload.data;
      })
      .addMatcher(isPending(getEntities, getEntity), state => {
        state.errorMessage = null;
        state.updateSuccess = false;
        state.loading = true;
      })
      .addMatcher(isPending(createEntity, updateEntity, partialUpdateEntity, deleteEntity), state => {
        state.errorMessage = null;
        state.updateSuccess = false;
        state.updating = true;
      });
  },
});

export const { reset } = AvatarSlice.actions;

// Reducer
export default AvatarSlice.reducer;
