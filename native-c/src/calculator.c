#include "slice_connector.h"
#include <math.h>

static int valid_number(double value) {
    return isfinite(value) && fabs(value) <= 1.0e12;
}

slice_status slice_add(double a, double b, double *result) {
    if (result == 0 || !valid_number(a) || !valid_number(b)) {
        return SLICE_ERROR_INVALID_ARGUMENT;
    }
    double value = a + b;
    if (!valid_number(value)) return SLICE_ERROR_OUT_OF_RANGE;
    *result = value;
    return SLICE_OK;
}

slice_status slice_multiply(double a, double b, double *result) {
    if (result == 0 || !valid_number(a) || !valid_number(b)) {
        return SLICE_ERROR_INVALID_ARGUMENT;
    }
    double value = a * b;
    if (!valid_number(value)) return SLICE_ERROR_OUT_OF_RANGE;
    *result = value;
    return SLICE_OK;
}
