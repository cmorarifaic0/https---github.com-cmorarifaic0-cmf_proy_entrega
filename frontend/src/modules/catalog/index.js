import * as actions from './actions';
import * as actionTypes from './actionTypes';
import catalogReducer from './reducer';
import * as selectors from './selectors';

export { default as FindProducts } from './components/FindProducts';
export { default as FindProductsResult } from './components/FindProductsResult';
export { default as ProductDetails } from './components/ProductDetails';

export default { actions, actionTypes, catalogReducer, selectors };