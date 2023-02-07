import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IBunkerReceivedNote } from 'app/shared/model/bunker-received-note.model';
import { getEntities } from './bunker-received-note.reducer';

export const BunkerReceivedNote = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const bunkerReceivedNoteList = useAppSelector(state => state.bunkerReceivedNote.entities);
  const loading = useAppSelector(state => state.bunkerReceivedNote.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="bunker-received-note-heading" data-cy="BunkerReceivedNoteHeading">
        Bunker Received Notes
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link
            to="/bunker-received-note/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Bunker Received Note
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {bunkerReceivedNoteList && bunkerReceivedNoteList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>ID</th>
                <th>Document Date And Time</th>
                <th>Document Display Number</th>
                <th>Voyage</th>
                <th />
              </tr>
            </thead>
            <tbody>
              {bunkerReceivedNoteList.map((bunkerReceivedNote, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/bunker-received-note/${bunkerReceivedNote.id}`} color="link" size="sm">
                      {bunkerReceivedNote.id}
                    </Button>
                  </td>
                  <td>
                    {bunkerReceivedNote.documentDateAndTime ? (
                      <TextFormat type="date" value={bunkerReceivedNote.documentDateAndTime} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{bunkerReceivedNote.documentDisplayNumber}</td>
                  <td>
                    {bunkerReceivedNote.voyage ? (
                      <Link to={`/voyage/${bunkerReceivedNote.voyage.id}`}>{bunkerReceivedNote.voyage.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/bunker-received-note/${bunkerReceivedNote.id}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/bunker-received-note/${bunkerReceivedNote.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/bunker-received-note/${bunkerReceivedNote.id}/delete`}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
                        <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && <div className="alert alert-warning">No Bunker Received Notes found</div>
        )}
      </div>
    </div>
  );
};

export default BunkerReceivedNote;
