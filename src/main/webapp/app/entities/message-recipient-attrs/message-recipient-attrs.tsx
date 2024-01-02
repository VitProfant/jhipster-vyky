import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './message-recipient-attrs.reducer';

export const MessageRecipientAttrs = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const messageRecipientAttrsList = useAppSelector(state => state.messageRecipientAttrs.entities);
  const loading = useAppSelector(state => state.messageRecipientAttrs.loading);

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
      <h2 id="message-recipient-attrs-heading" data-cy="MessageRecipientAttrsHeading">
        <Translate contentKey="jhipsterVykyApp.messageRecipientAttrs.home.title">Message Recipient Attrs</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="jhipsterVykyApp.messageRecipientAttrs.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link
            to="/message-recipient-attrs/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="jhipsterVykyApp.messageRecipientAttrs.home.createLabel">Create new Message Recipient Attrs</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {messageRecipientAttrsList && messageRecipientAttrsList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="jhipsterVykyApp.messageRecipientAttrs.id">Id</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('isRead')}>
                  <Translate contentKey="jhipsterVykyApp.messageRecipientAttrs.isRead">Is Read</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('isRead')} />
                </th>
                <th className="hand" onClick={sort('isDeleted')}>
                  <Translate contentKey="jhipsterVykyApp.messageRecipientAttrs.isDeleted">Is Deleted</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('isDeleted')} />
                </th>
                <th>
                  <Translate contentKey="jhipsterVykyApp.messageRecipientAttrs.message">Message</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="jhipsterVykyApp.messageRecipientAttrs.recipient">Recipient</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {messageRecipientAttrsList.map((messageRecipientAttrs, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/message-recipient-attrs/${messageRecipientAttrs.id}`} color="link" size="sm">
                      {messageRecipientAttrs.id}
                    </Button>
                  </td>
                  <td>{messageRecipientAttrs.isRead ? 'true' : 'false'}</td>
                  <td>{messageRecipientAttrs.isDeleted ? 'true' : 'false'}</td>
                  <td>
                    {messageRecipientAttrs.message ? (
                      <Link to={`/message/${messageRecipientAttrs.message.id}`}>{messageRecipientAttrs.message.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {messageRecipientAttrs.recipient ? (
                      <Link to={`/vyky-user/${messageRecipientAttrs.recipient.id}`}>{messageRecipientAttrs.recipient.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/message-recipient-attrs/${messageRecipientAttrs.id}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/message-recipient-attrs/${messageRecipientAttrs.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/message-recipient-attrs/${messageRecipientAttrs.id}/delete`)}
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
              <Translate contentKey="jhipsterVykyApp.messageRecipientAttrs.home.notFound">No Message Recipient Attrs found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default MessageRecipientAttrs;
