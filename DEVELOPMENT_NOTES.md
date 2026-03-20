# Development Notes

## Overall Approach

I followed a simple layered architecture while building this service.

The flow is straightforward:
- The controller receives the request
- Validation is handled using annotations
- The service layer processes the application
- Risk is calculated based on credit score
- Interest rate is derived using business rules
- EMI is calculated using the standard formula
- Based on eligibility checks, the application is either approved or rejected
- The result is stored for audit purposes

I tried to keep the flow easy to understand and not overcomplicate the design.

---

## Key Design Decisions

One important decision was to split responsibilities across services:
- `LoanService` handles the overall flow
- `EligibilityService` handles rejection logic
- `OfferService` handles interest and risk calculation

This made the code easier to read and test instead of putting everything in one place.

I used enums for things like risk band, employment type, etc., to avoid hardcoding strings and to make the code safer.

For response creation, I used static factory methods (`approved` and `rejected`) so that objects are always created in a valid state.

For financial calculations, I used BigDecimal to avoid precision issues.

---

## Trade-offs Considered

Initially, I thought of implementing a rule-based design (separate rule classes), but I removed it later.

It was adding extra complexity without much benefit for this assignment. The current approach is simpler and easier to maintain.

I also didn’t use a real database and instead used an in-memory repository. This keeps the project lightweight but means data won’t persist across restarts.

---

## Assumptions Made

- Only one offer is generated per application
- Interest rate is based on fixed rules (no external configs)
- EMI is calculated using standard reducing balance formula
- No authentication or security is required
- No concurrency handling is needed for this scope

---

## Improvements with More Time

If I had more time, I would:

- Add database support using JPA for persistence
- Add APIs to fetch application history
- Improve exception handling with custom error responses
- Add logging for better traceability
- Add Swagger/OpenAPI documentation
- Move business rules (interest rates, limits) to configuration

---

## Final Thoughts

I focused on keeping the solution clean and easy to understand rather than over-engineering it.

The idea was to make sure the business logic is correct and the structure is maintainable.