create view extended_control_actions as
select control_actions.id,
       done_time,
       successful,
       need_repair,
       control_type,
       comment,
       operation_type_id,
       employee_id,
       product_id,
       active,
       batch_id,
       kit_id
from control_actions
         left outer join products p on control_actions.product_id = p.id
         left outer join batches b on p.batch_id = b.id;

create view extended_acceptance_actions as
select a.id,
       a.done_time,
       a.employee_id,
       a.product_id,
       p.batch_id,
       p.active,
       b.kit_id
from acceptance_actions a
         left outer join products p on a.product_id = p.id
         left outer join batches b on p.batch_id = b.id;

create view extended_actions as
select actions.id,
       done_time,
       repair,
       operation_type_id,
       employee_id,
       product_id,
       active,
       batch_id,
       kit_id
from actions
         left outer join products p on actions.product_id = p.id
         left outer join batches b on p.batch_id = b.id;

create view extended_products as
select products.id,
       product_number,
       active,
       locked,
       batch_id,
       batch_number,
       kit_id
from products
         left outer join batches b on b.id = products.batch_id