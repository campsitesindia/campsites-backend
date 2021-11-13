import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './features.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import PageTitleWrapper from 'app/components/PageTitleWrapper';
import PageTitle from 'app/components/PageTitle';
import TextField from '@mui/material/TextField/TextField';

import { Card, CardHeader, Divider, Grid } from '@mui/material';
import Box from '@mui/material/Box/Box';
import CardContent from '@mui/material/CardContent/CardContent';
import { ThumbsUpIcon } from 'react-line-awesome';

export const FeaturesDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const featuresEntity = useAppSelector(state => state.features.entity);
  return (
    <div>
      <PageTitleWrapper>
        <PageTitle
          heading="Features"
          subHeading="Features Details"
          // docs="https://material-ui.com/components/text-fields/"
        />
      </PageTitleWrapper>
      <Grid item xs={12}>
        <Card>
          <CardHeader title="Input Fields" />

          <Divider />
          <CardContent>
            <Box
              component="form"
              sx={{
                '& .MuiTextField-root': { m: 1, width: '25ch' },
              }}
              noValidate
              autoComplete="off"
            >
              <div>
                <TextField required id="outlined-required" label="Required" defaultValue="Hello World" />
                <TextField disabled id="outlined-disabled" label="Disabled" defaultValue="Hello World" />
                <TextField id="outlined-password-input" label="Password" type="password" autoComplete="current-password" />
                <TextField
                  id="outlined-read-only-input"
                  label="Read Only"
                  defaultValue="Hello World"
                  InputProps={{
                    readOnly: true,
                  }}
                />
                <TextField
                  id="outlined-number"
                  label="Number"
                  type="number"
                  InputLabelProps={{
                    shrink: true,
                  }}
                />
                <TextField id="outlined-search" label="Search field" type="search" />
                <TextField id="outlined-helperText" label="Helper text" defaultValue="Default Value" helperText="Some important text" />
              </div>
              <div>
                <TextField required id="filled-required" label="Required" defaultValue="Hello World" variant="filled" />
                <TextField disabled id="filled-disabled" label="Disabled" defaultValue="Hello World" variant="filled" />
                <TextField id="filled-password-input" label="Password" type="password" autoComplete="current-password" variant="filled" />
                <TextField
                  id="filled-read-only-input"
                  label="Read Only"
                  defaultValue="Hello World"
                  InputProps={{
                    readOnly: true,
                  }}
                  variant="filled"
                />
                <TextField
                  id="filled-number"
                  label="Number"
                  type="number"
                  InputLabelProps={{
                    shrink: true,
                  }}
                  variant="filled"
                />
                <TextField id="filled-search" label="Search field" type="search" variant="filled" />
                <TextField
                  id="filled-helperText"
                  label="Helper text"
                  defaultValue="Default Value"
                  helperText="Some important text"
                  variant="filled"
                />
              </div>
              <div>
                <TextField required id="standard-required" label="Required" defaultValue="Hello World" variant="standard" />
                <TextField disabled id="standard-disabled" label="Disabled" defaultValue="Hello World" variant="standard" />
                <TextField
                  id="standard-password-input"
                  label="Password"
                  type="password"
                  autoComplete="current-password"
                  variant="standard"
                />
                <TextField
                  id="standard-read-only-input"
                  label="Read Only"
                  defaultValue="Hello World"
                  InputProps={{
                    readOnly: true,
                  }}
                  variant="standard"
                />
                <TextField
                  id="standard-number"
                  label="Number"
                  type="number"
                  InputLabelProps={{
                    shrink: true,
                  }}
                  variant="standard"
                />
                <TextField id="standard-search" label="Search field" type="search" variant="standard" />
                <TextField
                  id="standard-helperText"
                  label="Helper text"
                  defaultValue="Default Value"
                  helperText="Some important text"
                  variant="standard"
                />
              </div>
            </Box>
          </CardContent>
        </Card>
      </Grid>
      <Row>
        <Col md="8">
          <ThumbsUpIcon />
          111
          <i className="text-3xl las la-credit-card" />
          <i className="text-3xl las la-key" />
          <i className="text-3xl las la-luggage-cart" />
          <i className="text-3xl las la-shower" />
          <i className="text-3xl las la-smoking" />
          <i className="text-3xl las la-snowflake" />
          <i className="text-3xl las la-spa" />
          <i className="text-3xl las la-suitcase" />
          <i className="text-3xl las la-suitcase-rolling" />
          <i className="text-3xl las la-swimmer" />
          <i className="text-3xl las la-swimming-pool" />
          <i className="text-3xl las la-tv" />
          <i className="text-3xl las la-umbrella-beach" />
          <i className="text-3xl las la-utensils" />
          <i className="text-3xl las la-wheelchair" />
          <i className="text-3xl las la-wifi" />
          <i className="text-3xl las la-baby-carriage" />
          <i className="text-3xl las la-bath" />
          <i className="text-3xl las la-bed" />
          <i className="text-3xl las la-briefcase" />
          <i className="text-3xl las la-car" />
          <i className="text-3xl las la-cocktail" />
          <i className="text-3xl las la-coffee" />
          <i className="text-3xl las la-concierge-bell" />
          <i className="text-3xl las la-dice" />
          <i className="text-3xl las la-dumbbell" />
          <i className="text-3xl las la-hot-tub" />
          <i className="text-3xl las la-infinity" />
          <h2 data-cy="featuresDetailsHeading">
            <Translate contentKey="campsitesindiaApp.features.detail.title">Features</Translate>
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="id">
                <Translate contentKey="global.field.id">ID</Translate>
              </span>
            </dt>
            <dd>{featuresEntity.id}</dd>
            <dt>
              <span id="title">
                <Translate contentKey="campsitesindiaApp.features.title">Title</Translate>
              </span>
            </dt>
            <dd>{featuresEntity.title}</dd>
            <dt>
              <span id="count">
                <Translate contentKey="campsitesindiaApp.features.count">Count</Translate>
              </span>
            </dt>
            <dd>{featuresEntity.count}</dd>
            <dt>
              <span id="thumbnail">
                <Translate contentKey="campsitesindiaApp.features.thumbnail">Thumbnail</Translate>
              </span>
            </dt>
            <dd>{featuresEntity.thumbnail}</dd>
            <dt>
              <span id="icon">
                <Translate contentKey="campsitesindiaApp.features.icon">Icon</Translate>
              </span>
            </dt>
            <dd>{featuresEntity.icon}</dd>
            <dt>
              <span id="color">
                <Translate contentKey="campsitesindiaApp.features.color">Color</Translate>
              </span>
            </dt>
            <dd>{featuresEntity.color}</dd>
            <dt>
              <span id="imgIcon">
                <Translate contentKey="campsitesindiaApp.features.imgIcon">Img Icon</Translate>
              </span>
            </dt>
            <dd>{featuresEntity.imgIcon}</dd>
            <dt>
              <span id="description">
                <Translate contentKey="campsitesindiaApp.features.description">Description</Translate>
              </span>
            </dt>
            <dd>{featuresEntity.description}</dd>
            <dt>
              <span id="parent">
                <Translate contentKey="campsitesindiaApp.features.parent">Parent</Translate>
              </span>
            </dt>
            <dd>{featuresEntity.parent}</dd>
            <dt>
              <span id="taxonomy">
                <Translate contentKey="campsitesindiaApp.features.taxonomy">Taxonomy</Translate>
              </span>
            </dt>
            <dd>{featuresEntity.taxonomy}</dd>
            <dt>
              <span id="createdBy">
                <Translate contentKey="campsitesindiaApp.features.createdBy">Created By</Translate>
              </span>
            </dt>
            <dd>{featuresEntity.createdBy}</dd>
            <dt>
              <span id="createdDate">
                <Translate contentKey="campsitesindiaApp.features.createdDate">Created Date</Translate>
              </span>
            </dt>
            <dd>
              {featuresEntity.createdDate ? <TextFormat value={featuresEntity.createdDate} type="date" format={APP_DATE_FORMAT} /> : null}
            </dd>
            <dt>
              <span id="updatedBy">
                <Translate contentKey="campsitesindiaApp.features.updatedBy">Updated By</Translate>
              </span>
            </dt>
            <dd>
              {featuresEntity.updatedBy ? <TextFormat value={featuresEntity.updatedBy} type="date" format={APP_DATE_FORMAT} /> : null}
            </dd>
            <dt>
              <span id="updateDate">
                <Translate contentKey="campsitesindiaApp.features.updateDate">Update Date</Translate>
              </span>
            </dt>
            <dd>
              {featuresEntity.updateDate ? <TextFormat value={featuresEntity.updateDate} type="date" format={APP_DATE_FORMAT} /> : null}
            </dd>
          </dl>
          <Button tag={Link} to="/features" replace color="info" data-cy="entityDetailsBackButton">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/features/${featuresEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.edit">Edit</Translate>
            </span>
          </Button>
        </Col>
      </Row>
    </div>
  );
};

export default FeaturesDetail;
