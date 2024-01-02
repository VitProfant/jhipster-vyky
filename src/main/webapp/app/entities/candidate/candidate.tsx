import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './candidate.reducer';

export const Candidate = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const candidateList = useAppSelector(state => state.candidate.entities);
  const loading = useAppSelector(state => state.candidate.loading);

  const getAllEntities = () => {
    dispatch(
      getEntities({
        sort: `${sortState.sort},${sortState.order}`,
      }),
    );
  };

  const sortEntities = () => {
    getAllEntities();
    const endURL = `?sort=${sortState.sort},${sortState.order}`;
    if (pageLocation.search !== endURL) {
      navigate(`${pageLocation.pathname}${endURL}`);
    }
  };

  useEffect(() => {
    sortEntities();
  }, [sortState.order, sortState.sort]);

  const sort = p => () => {
    setSortState({
      ...sortState,
      order: sortState.order === ASC ? DESC : ASC,
      sort: p,
    });
  };

  const handleSyncList = () => {
    sortEntities();
  };

  const getSortIconByFieldName = (fieldName: string) => {
    const sortFieldName = sortState.sort;
    const order = sortState.order;
    if (sortFieldName !== fieldName) {
      return faSort;
    } else {
      return order === ASC ? faSortUp : faSortDown;
    }
  };

  return (
    <div>
      <h2 id="candidate-heading" data-cy="CandidateHeading">
        <Translate contentKey="jhipsterVykyApp.candidate.home.title">Candidates</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="jhipsterVykyApp.candidate.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/candidate/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="jhipsterVykyApp.candidate.home.createLabel">Create new Candidate</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {candidateList && candidateList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="jhipsterVykyApp.candidate.id">Id</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('login')}>
                  <Translate contentKey="jhipsterVykyApp.candidate.login">Login</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('login')} />
                </th>
                <th className="hand" onClick={sort('password')}>
                  <Translate contentKey="jhipsterVykyApp.candidate.password">Password</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('password')} />
                </th>
                <th className="hand" onClick={sort('email')}>
                  <Translate contentKey="jhipsterVykyApp.candidate.email">Email</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('email')} />
                </th>
                <th className="hand" onClick={sort('token')}>
                  <Translate contentKey="jhipsterVykyApp.candidate.token">Token</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('token')} />
                </th>
                <th className="hand" onClick={sort('createdAt')}>
                  <Translate contentKey="jhipsterVykyApp.candidate.createdAt">Created At</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('createdAt')} />
                </th>
                <th className="hand" onClick={sort('deletedAt')}>
                  <Translate contentKey="jhipsterVykyApp.candidate.deletedAt">Deleted At</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('deletedAt')} />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {candidateList.map((candidate, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/candidate/${candidate.id}`} color="link" size="sm">
                      {candidate.id}
                    </Button>
                  </td>
                  <td>{candidate.login}</td>
                  <td>{candidate.password}</td>
                  <td>{candidate.email}</td>
                  <td>{candidate.token}</td>
                  <td>{candidate.createdAt ? <TextFormat type="date" value={candidate.createdAt} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{candidate.deletedAt ? <TextFormat type="date" value={candidate.deletedAt} format={APP_DATE_FORMAT} /> : null}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/candidate/${candidate.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/candidate/${candidate.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/candidate/${candidate.id}/delete`)}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="jhipsterVykyApp.candidate.home.notFound">No Candidates found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Candidate;
